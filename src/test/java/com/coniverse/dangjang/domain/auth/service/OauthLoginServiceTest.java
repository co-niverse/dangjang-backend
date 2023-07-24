package com.coniverse.dangjang.domain.auth.service;

import static org.assertj.core.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;

import com.coniverse.dangjang.domain.auth.dto.AuthToken.AuthToken;
import com.coniverse.dangjang.domain.auth.dto.Response.LoginResponse;
import com.coniverse.dangjang.domain.auth.dto.request.KakaoLoginRequest;
import com.coniverse.dangjang.domain.auth.service.authToken.AuthTokensGenerator;
import com.coniverse.dangjang.domain.user.dto.SignUpRequest;
import com.coniverse.dangjang.domain.user.dto.TestDto;
import com.coniverse.dangjang.domain.user.dto.UserResponse;
import com.coniverse.dangjang.domain.user.exception.NonExistentUserException;
import com.coniverse.dangjang.domain.user.infrastructure.KakaoInfoResponse;
import com.coniverse.dangjang.domain.user.service.UserService;

/**
 * @author EVE
 * @since 1.0
 */
@SpringBootTest
public class OauthLoginServiceTest {
	@Autowired
	private OauthLoginService oAuthLoginService;

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

		//given 회원가입
		SignUpRequest signUpRequest = TestDto.getSignUpRequest();
		userService.signUp(signUpRequest);

		KakaoLoginRequest kakaoLoginParams = new KakaoLoginRequest();
		kakaoLoginParams.setAccessToken("4J-zgwK68lN3RIm8iy1Qv0EGE54mbyOrVc-X1cf1CinJXgAAAYk1SMch");

		//when
		LoginResponse actual = oAuthLoginService.login(kakaoLoginParams);
		//then
		assertThat(actual).isNotNull();
	}

	@Test
	@WithAnonymousUser
	void JWT_반환한다() throws NonExistentUserException {
		// given

		SignUpRequest signUpRequest = TestDto.getSignUpRequest();
		userService.signUp(signUpRequest);

		KakaoInfoResponse kakaoInfoResponse = new KakaoInfoResponse();
		kakaoInfoResponse.setId("A1234567");
		kakaoInfoResponse.setConnectedAt(new Date());

		//when 유저 존재 확인
		UserResponse userInfo = userService.findUser(kakaoInfoResponse);
		//then
		if (userInfo != null) {
			AuthToken authToken = authTokensGenerator.generate(userInfo.oauthId());
			assertThat(authToken).isNotNull();
		}
	}

	@Test()
	void 존재하지_않는_유저_여부_확인하기() throws NonExistentUserException {
		//given
		KakaoInfoResponse kakaoInfoResponse = new KakaoInfoResponse();
		kakaoInfoResponse.setId("2878733654L");
		kakaoInfoResponse.setConnectedAt(new Date());

		Assertions.assertThrows(NonExistentUserException.class, () -> {
			userService.findUser(kakaoInfoResponse);
		});
	}
}
