package com.coniverse.dangjang.domain.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;

import com.coniverse.dangjang.domain.auth.dto.AuthToken.AuthTokens;
import com.coniverse.dangjang.domain.auth.dto.OAuthProvider;
import com.coniverse.dangjang.domain.auth.dto.Response.LoginResponse;
import com.coniverse.dangjang.domain.auth.dto.request.KakaoLoginParams;
import com.coniverse.dangjang.domain.user.dto.UserInfo;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.infrastructure.KakaoInfoResponse;
import com.coniverse.dangjang.domain.user.repository.UserRepository;
import com.coniverse.dangjang.domain.user.service.UserService;
import com.coniverse.dangjang.global.config.TestConfig;
import com.coniverse.dangjang.global.exception.NonExistentUserException;

/**
 * @author EVE
 * @since 1.0
 */
@SpringBootTest
@Import(TestConfig.class)
public class OAuthLoginServiceTest {
	@Autowired
	private OAuthLoginService oAuthLoginService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuthTokensGenerator authTokensGenerator;
	@Autowired
	private UserService userService;

	@Test
	@WithAnonymousUser
	void 로그인_실패한다() throws Exception {

		//given
		KakaoLoginParams kakaoLoginParams = new KakaoLoginParams();
		kakaoLoginParams.setAccessToken("4J-zgwK68lN3RIm8iy1Qv0EGE54mbyOrVc-X1cf1CinJXgAAAYk1SMchfail");

		//then
		Assertions.assertThrows(NonExistentUserException.class, () -> {
			oAuthLoginService.login(kakaoLoginParams);
		});

	}

	@Test
	void 로그인을_성공한다() {

		//given
		KakaoLoginParams kakaoLoginParams = new KakaoLoginParams();
		kakaoLoginParams.setAccessToken("4J-zgwK68lN3RIm8iy1Qv0EGE54mbyOrVc-X1cf1CinJXgAAAYk1SMch");
		//User user = User.builder().oauth(5555L).nickname("nickname").oAuthProvider(OAuthProvider.KAKAO).build();
		User user = User.builder().oauth(5555L).nickname("nickname").oAuthProvider(OAuthProvider.KAKAO).build();
		userRepository.save(user);
		LoginResponse loginResponse = mock(LoginResponse.class);
		// System.out.println("countLogin : " + oAuthLoginService.login(kakaoLoginParams).getNickname());
		;
		// given(oAuthLoginService.login(kakaoLoginParams)).willReturn(loginResponse);
		// when(oAuthLoginService.login(kakaoLoginParams)).thenReturn(loginResponse);

		//when
		LoginResponse actual = oAuthLoginService.login(kakaoLoginParams);
		// //
		// // //then
		assertThat(actual).isNotNull();
	}

	@Test
	@WithAnonymousUser
	void JWT_반환한다() throws Exception {
		// given
		KakaoInfoResponse kakaoInfoResponse = new KakaoInfoResponse();
		kakaoInfoResponse.setId(287873365589L);
		kakaoInfoResponse.setConnected_at(new Date());
		//유저 생성
		User user = User.builder().oauth(287873365589L).nickname("nickname").oAuthProvider(OAuthProvider.KAKAO).build();
		userRepository.save(user);

		//유저 존재 확인
		UserInfo userInfo = userService.findUser(kakaoInfoResponse);
		if (userInfo != null) {
			AuthTokens authTokens = authTokensGenerator.generate(userInfo.getOauthId());
			assertThat(authTokens).isNotNull();
		}
	}

	@Test()
	void 새로운_유저를_추가한다() {
		//given
		KakaoInfoResponse kakaoInfoResponse = new KakaoInfoResponse();
		kakaoInfoResponse.setId(2878733655L);
		kakaoInfoResponse.setConnected_at(new Date());
		Long userId = userService.newMember(kakaoInfoResponse);
		assertThat(userId).isNotNull();
	}

	@Test()
	void 유저_여부_확인하기() throws Exception {
		//given
		KakaoInfoResponse kakaoInfoResponse = new KakaoInfoResponse();
		kakaoInfoResponse.setId(2878733654L);
		kakaoInfoResponse.setConnected_at(new Date());

		Assertions.assertThrows(NonExistentUserException.class, () -> {
			userService.findUser(kakaoInfoResponse);
		});
	}
}
