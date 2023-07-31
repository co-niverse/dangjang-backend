package com.coniverse.dangjang.domain.auth.service;

import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;

import com.coniverse.dangjang.domain.auth.dto.request.KakaoLoginRequest;
import com.coniverse.dangjang.domain.auth.dto.response.LoginResponse;
import com.coniverse.dangjang.domain.infrastructure.auth.dto.KakaoInfoResponse;
import com.coniverse.dangjang.domain.user.exception.NonExistentUserException;
import com.coniverse.dangjang.domain.user.repository.UserRepository;
import com.coniverse.dangjang.domain.user.service.UserService;

/**
 * @author EVE, TEO
 * @since 1.0.0
 */
@SpringBootTest
class OauthLoginServiceTest {
	@Autowired
	private OauthLoginService oauthLoginService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuthTokenGenerator authTokenGenerator;
	@Autowired
	private UserService userService;
	private static final String accessToken = "12345678";

	@Test
	@WithAnonymousUser
	void 로그인_실패한다() throws NonExistentUserException {

		//given
		KakaoLoginRequest kakaoLoginParams = new KakaoLoginRequest(accessToken);

		//then
		Assertions.assertThrows(NonExistentUserException.class, () -> {
			oauthLoginService.login(kakaoLoginParams);
		});

	}

	@Test
	void 로그인을_성공한다() {
		//given
		KakaoLoginRequest kakaoLoginParams = new KakaoLoginRequest(accessToken);
		userRepository.save(유저_가은());

		//when
		LoginResponse actual = oauthLoginService.login(kakaoLoginParams);
		//then
		assertThat(actual).isNotNull();
	}

	// @Test
	// void JWT_반환한다() throws NonExistentUserException {
	// 	// given
	// 	KakaoInfoResponse kakaoInfoResponse = new KakaoInfoResponse("12345678");
	// 	userRepository.save(유저_가은());
	//
	// 	// when
	// 	User findUser = userService.findUserByOauthId(kakaoInfoResponse.getOauthId());
	//
	// 	// then
	// 	if (findUser != null) {
	// 		AuthToken authToken = authTokenGenerator.generate(findUser.getOauthId());
	// 		assertThat(authToken).isNotNull();
	// 	}
	// }

	@Test
	void 새로운_유저를_추가한다() {
		//given
		KakaoInfoResponse kakaoInfoResponse = new KakaoInfoResponse("12345678");
		String userId = userService.signUp(kakaoInfoResponse);
		assertThat(userId).isNotNull();
	}

	// @Test
	// void 존재하지_않는_유저_여부_확인하기() throws NonExistentUserException {
	// 	//given
	// 	KakaoInfoResponse kakaoInfoResponse = new KakaoInfoResponse("12345678");
	//
	// 	Assertions.assertThrows(NonExistentUserException.class, () -> {
	// 		userService.findUserByOauthId(kakaoInfoResponse.getOauthId());
	// 	});
	// }
}
