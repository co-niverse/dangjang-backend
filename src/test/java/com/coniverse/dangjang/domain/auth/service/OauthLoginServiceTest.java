package com.coniverse.dangjang.domain.auth.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;

import com.coniverse.dangjang.domain.auth.dto.AuthToken.AuthToken;
import com.coniverse.dangjang.domain.auth.dto.OauthProvider;
import com.coniverse.dangjang.domain.auth.dto.Response.LoginResponse;
import com.coniverse.dangjang.domain.auth.dto.request.KakaoLoginRequest;
import com.coniverse.dangjang.domain.auth.service.authToken.AuthTokensGenerator;
import com.coniverse.dangjang.domain.user.dto.UserResponse;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.exception.NonExistentUserException;
import com.coniverse.dangjang.domain.user.infrastructure.KakaoInfoResponse;
import com.coniverse.dangjang.domain.user.repository.UserRepository;
import com.coniverse.dangjang.domain.user.service.UserService;

/**
 * @author EVE
 * @since 1.0
 */
@SpringBootTest
class OauthLoginServiceTest {
	@Autowired
	private OauthLoginService oAuthLoginService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuthTokensGenerator authTokensGenerator;
	@Autowired
	private UserService userService;

	@Test
	@WithAnonymousUser
	void 로그인_실패한다() throws NonExistentUserException {

		//given
		KakaoLoginRequest kakaoLoginParams = new KakaoLoginRequest();
		kakaoLoginParams.setAccessToken("4J-zgwK68lN3RIm8iy1Qv0EGE54mbyOrVc-X1cf1CinJXgAAAYk1SMchfail");

		//then
		Assertions.assertThrows(NonExistentUserException.class, () -> {
			oAuthLoginService.login(kakaoLoginParams);
		});

	}

	@Test
	void 로그인을_성공한다() {

		//given
		KakaoLoginRequest kakaoLoginParams = new KakaoLoginRequest();
		kakaoLoginParams.setAccessToken("4J-zgwK68lN3RIm8iy1Qv0EGE54mbyOrVc-X1cf1CinJXgAAAYk1SMch");
		User user = User.builder().oauthId("A1234567").nickname("nickname").oauthProvider(OauthProvider.KAKAO).build();
		userRepository.save(user);
		LoginResponse loginResponse = mock(LoginResponse.class);

		//when
		LoginResponse actual = oAuthLoginService.login(kakaoLoginParams);
		//then
		assertThat(actual).isNotNull();
	}

	@Test
	@WithAnonymousUser
	void JWT_반환한다() throws NonExistentUserException {
		// given
		KakaoInfoResponse kakaoInfoResponse = new KakaoInfoResponse();
		kakaoInfoResponse.setId("A1234567");
		kakaoInfoResponse.setConnectedAt(new Date());
		//유저 생성
		User user = User.builder().oauthId("A1234567").nickname("nickname").oauthProvider(OauthProvider.KAKAO).build();
		userRepository.save(user);

		//유저 존재 확인
		UserResponse userResponse = userService.findUser(kakaoInfoResponse);
		if (userResponse != null) {
			AuthToken authToken = authTokensGenerator.generate(userResponse.oauthId());
			assertThat(authToken).isNotNull();
		}
	}

	@Test()
	void 새로운_유저를_추가한다() {
		//given
		KakaoInfoResponse kakaoInfoResponse = new KakaoInfoResponse();
		kakaoInfoResponse.setId("A1234567");
		kakaoInfoResponse.setConnectedAt(new Date());
		String userId = userService.signUp(kakaoInfoResponse);
		assertThat(userId).isNotNull();
	}

	@Test()
	void 존재하지_않는_유저_여부_확인하기() throws NonExistentUserException {
		//given
		KakaoInfoResponse kakaoInfoResponse = new KakaoInfoResponse();
		kakaoInfoResponse.setId("A1234567");
		kakaoInfoResponse.setConnectedAt(new Date());

		Assertions.assertThrows(NonExistentUserException.class, () -> {
			userService.findUser(kakaoInfoResponse);
		});
	}
}
