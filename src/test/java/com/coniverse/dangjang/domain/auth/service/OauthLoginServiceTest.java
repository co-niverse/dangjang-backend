package com.coniverse.dangjang.domain.auth.service;

import static com.coniverse.dangjang.fixture.LoginFixture.*;
import static com.coniverse.dangjang.fixture.NotificationFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.coniverse.dangjang.domain.auth.dto.request.KakaoLoginRequest;
import com.coniverse.dangjang.domain.auth.dto.response.LoginResponse;
import com.coniverse.dangjang.domain.auth.entity.BlackToken;
import com.coniverse.dangjang.domain.auth.repository.BlackTokenRepository;
import com.coniverse.dangjang.domain.auth.repository.RefreshTokenRepository;
import com.coniverse.dangjang.domain.notification.repository.UserFcmTokenRepository;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.exception.NonExistentUserException;
import com.coniverse.dangjang.domain.user.exception.WithdrawalUserException;
import com.coniverse.dangjang.domain.user.repository.UserRepository;
import com.coniverse.dangjang.global.exception.BlackTokenException;
import com.coniverse.dangjang.global.exception.InvalidTokenException;

import jakarta.persistence.EntityManager;

/**
 * @author EVE, TEO
 * @since 1.0.0
 */
@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OauthLoginServiceTest {
	@Autowired
	private OauthLoginService oauthLoginService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BlackTokenRepository blackTokenRepository;
	private final String fcmToken = "fcmToken";
	@Autowired
	private EntityManager entityManager;
	@Autowired
	private UserFcmTokenRepository userFcmTokenRepository;
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@AfterEach
	void tearDown() {
		userRepository.deleteAll();
	}

	@Order(100)
	@Test
	void 가입된_유저가_아니면_로그인을_실패한다() {
		//given
		KakaoLoginRequest request = 카카오_로그인_요청();

		// when & then
		assertThatThrownBy(() -> oauthLoginService.login(request, fcmToken))
			.isInstanceOf(NonExistentUserException.class);
	}

	@Order(200)
	@Test
	void 가입된_유저면_로그인을_성공한다() {
		//given
		User 이브 = userRepository.save(유저_이브());
		entityManager.flush();
		KakaoLoginRequest request = 카카오_로그인_요청();
		int tokenCount = 0;
		//when
		LoginResponse response = oauthLoginService.login(request, fcmToken);

		//then
		assertAll(
			() -> assertThat(response.nickname()).isEqualTo(이브.getNickname()),
			() -> assertThat(response.dangjangClub()).isFalse(),
			() -> assertThat(response.healthConnect()).isFalse()
		);
	}

	@Order(300)
	@Test
	void 존재하는_사용자라면_auth토큰을_발급해준다() {
		User 이브 = userRepository.save(유저_이브());

		//when
		String accessToken = oauthLoginService.getAuthToken(이브.getNickname());

		//then
		assertAll(
			() -> assertThat(accessToken).isNotBlank(),
			() -> assertThat(refreshTokenRepository.findById(accessToken).isPresent()).isTrue()
		);

	}

	@Order(400)
	@Test
	void 존재하지_않는_사용자라면_auth토큰_발급시_오류발생한다() {
		//given
		String nickname = "testNicknametest";

		//when&then
		assertThatThrownBy(() -> oauthLoginService.getAuthToken(nickname))
			.isInstanceOf(NonExistentUserException.class);

	}

	@Order(500)
	@Test
	void refreshToken이_유효하면_auth토큰을_재발급한다() {
		//given
		User 이브 = userRepository.save(유저_이브());
		String accessToken = oauthLoginService.getAuthToken(이브.getNickname());
		String header = "Bearer " + accessToken;

		//when
		String newAccessToken = oauthLoginService.reissueToken(header);

		//then
		assertAll(
			() -> assertThat(newAccessToken).isNotBlank()
		);
	}

	@Order(600)
	@Test
	void refreshToken이_유효하지_않으면_예외를_던진다() {
		//given
		User 이브 = userRepository.save(유저_이브());
		String accessToken = oauthLoginService.getAuthToken(이브.getNickname());
		String header = "Bearer " + accessToken + "test";

		//then
		assertThatException().isThrownBy(() -> oauthLoginService.reissueToken(header)).isInstanceOf(InvalidTokenException.class);
	}

	@Order(700)
	@Test
	void 로그아웃을_성공한다() {
		//given
		User 이브 = userRepository.save(유저_이브());
		userFcmTokenRepository.save(사용자_fcmToken_엔티티(fcmToken, 이브));
		String accessToken = oauthLoginService.getAuthToken(이브.getNickname());
		String header = "Bearer " + accessToken;
		//when
		oauthLoginService.logout(header, fcmToken);
		//then
		assertThat(blackTokenRepository.findById(accessToken).isPresent()).isTrue();
	}

	@Order(800)
	@Test
	void 로그아웃을_실패한다() {
		//given
		User 이브 = userRepository.save(유저_이브());
		String accessToken = oauthLoginService.getAuthToken(이브.getNickname());
		String header = "Bearer " + accessToken + "test";
		//when & then
		assertThatThrownBy(() -> oauthLoginService.logout(header, fcmToken)).isInstanceOf(InvalidTokenException.class);
	}

	@Test
	void inactive_유저는_로그인에_실패한다() {
		//given
		User 이브 = 유저_이브();
		이브.inactivate();
		userRepository.save(이브);
		KakaoLoginRequest request = 카카오_로그인_요청();

		//when & then
		assertThatThrownBy(() -> oauthLoginService.login(request, fcmToken))
			.isInstanceOf(WithdrawalUserException.class);
	}

	@Test
	void 블랙토큰이면_예외를_던진다() {
		//given
		BlackToken 블랙토큰 = BlackToken.builder().token("accessToken").expirationTime(100000).build();
		blackTokenRepository.save(블랙토큰);
		//when&then
		assertThatThrownBy(() -> oauthLoginService.validBlackToken("accessToken"))
			.isInstanceOf(BlackTokenException.class);
	}

	@Test
	void 블랙토큰이_아니면_예외를_던지지_않는다() {

		//when&then
		// assertThatThrownBy(() -> oauthLoginService.validBlackToken("accessToken"));
	}
}
