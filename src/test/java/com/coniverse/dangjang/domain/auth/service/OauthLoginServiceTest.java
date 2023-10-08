package com.coniverse.dangjang.domain.auth.service;

import static com.coniverse.dangjang.fixture.LoginFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.coniverse.dangjang.domain.auth.dto.request.KakaoLoginRequest;
import com.coniverse.dangjang.domain.auth.dto.response.LoginResponse;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.exception.NonExistentUserException;
import com.coniverse.dangjang.domain.user.repository.UserRepository;
import com.coniverse.dangjang.global.exception.InvalidTokenException;

/**
 * @author EVE, TEO
 * @since 1.0.0
 */
@SpringBootTest
@Transactional
class OauthLoginServiceTest {
	@Autowired
	private OauthLoginService oauthLoginService;
	@Autowired
	private UserRepository userRepository;
	private final String fcmToken = "fcmToken";

	@Test
	void 가입된_유저가_아니면_로그인을_실패한다() {
		//given
		KakaoLoginRequest request = 카카오_로그인_요청();

		// when & then
		assertThatThrownBy(() -> oauthLoginService.login(request, fcmToken))
			.isInstanceOf(NonExistentUserException.class);
	}

	@Test
	void 가입된_유저면_로그인을_성공한다() {
		//given
		User 이브 = userRepository.save(유저_이브());
		KakaoLoginRequest request = 카카오_로그인_요청();

		//when
		LoginResponse response = oauthLoginService.login(request, fcmToken);

		//then
		assertAll(
			() -> assertThat(response.nickname()).isEqualTo(이브.getNickname()),
			() -> assertThat(response.dangjangClub()).isFalse(),
			() -> assertThat(response.healthConnect()).isFalse()
		);
	}

	@Test
	void 존재하는_사용자라면_auth토큰을_발급해준다() {
		User 이브 = userRepository.save(유저_이브());

		//when
		String accessToken = oauthLoginService.getAuthToken(이브.getNickname());

		//then
		assertAll(
			() -> assertThat(accessToken).isNotBlank()
		);

	}

	@Test
	void 존재하지_않는_사용자라면_auth토큰_발급시_오류발생한다() {
		//given
		String nickname = "testNicknametest";

		//when&then
		assertThatThrownBy(() -> oauthLoginService.getAuthToken(nickname))
			.isInstanceOf(NonExistentUserException.class);

	}

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

	@Test
	void refreshToken이_유효하지_않으면_예외를_던진다() {
		//given
		User 이브 = userRepository.save(유저_이브());
		String accessToken = oauthLoginService.getAuthToken(이브.getNickname());
		String header = "Bearer " + accessToken + "test";

		//then
		assertThatException().isThrownBy(() -> oauthLoginService.reissueToken(header)).isInstanceOf(InvalidTokenException.class);
	}
}
