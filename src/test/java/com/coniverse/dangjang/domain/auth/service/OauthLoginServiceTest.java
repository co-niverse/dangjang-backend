package com.coniverse.dangjang.domain.auth.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;

import com.coniverse.dangjang.domain.auth.dto.AuthToken.AuthToken;
import com.coniverse.dangjang.domain.auth.dto.OauthProvider;
import com.coniverse.dangjang.domain.auth.dto.Response.LoginResponse;
import com.coniverse.dangjang.domain.auth.dto.request.KakaoLoginRequest;
import com.coniverse.dangjang.domain.auth.service.authToken.AuthTokensGenerator;
import com.coniverse.dangjang.domain.user.dto.SignUpRequest;
import com.coniverse.dangjang.domain.user.dto.UserInfo;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.entity.UserId;
import com.coniverse.dangjang.domain.user.exception.NonExistentUserException;
import com.coniverse.dangjang.domain.user.infrastructure.KakaoInfoResponse;
import com.coniverse.dangjang.domain.user.repository.UserRepository;
import com.coniverse.dangjang.domain.user.service.UserService;
import com.coniverse.dangjang.global.config.TestConfig;

/**
 * @author EVE
 * @since 1.0
 */
@SpringBootTest
@Import(TestConfig.class)
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
		User user = User.builder().oauthId(5555L).nickname("nickname").oAuthProvider(OauthProvider.KAKAO).build();
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
		kakaoInfoResponse.setId(287873365589L);
		kakaoInfoResponse.setConnectedAt(new Date());
		//유저 생성
		User user = User.builder().userId(new UserId("dsfsfxwxe", "kakao")).nickname("nickname").build();
		userRepository.save(user);

		//유저 존재 확인
		UserInfo userInfo = userService.findUser(kakaoInfoResponse);
		if (userInfo != null) {
			AuthToken authToken = authTokensGenerator.generate(userInfo.getOauthId());
			assertThat(authToken).isNotNull();
		}
	}

	@Test()
	void 새로운_유저를_추가한다() {
		//given
		SignUpRequest signUpRequest = new SignUpRequest();
		signUpRequest.setNickname("nickname");
		signUpRequest.setDiabetes(false);
		signUpRequest.setBirthday(new Date());
		signUpRequest.setHeight(180);
		signUpRequest.setInjection(true);
		signUpRequest.setMedicine(true);
		signUpRequest.setActivityAmount("HIGH");
		UserId userId = userService.signUp(signUpRequest);
		assertThat(userId).isNotNull();
	}

	@Test()
	void 존재하지_않는_유저_여부_확인하기() throws NonExistentUserException {
		//given
		KakaoInfoResponse kakaoInfoResponse = new KakaoInfoResponse();
		kakaoInfoResponse.setId(2878733654L);
		kakaoInfoResponse.setConnectedAt(new Date());

		Assertions.assertThrows(NonExistentUserException.class, () -> {
			userService.findUser(kakaoInfoResponse);
		});
	}
}
