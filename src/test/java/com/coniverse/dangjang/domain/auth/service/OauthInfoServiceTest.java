package com.coniverse.dangjang.domain.auth.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coniverse.dangjang.domain.auth.dto.request.KakaoLoginRequest;
import com.coniverse.dangjang.domain.auth.dto.request.NaverLoginRequest;
import com.coniverse.dangjang.domain.user.exception.InvalidAuthenticationException;

@SpringBootTest
class OauthInfoServiceTest {
	@Autowired
	private ProductOauthInfoService oAuthInfoService;

	@Test
	void 카카오_사용자_정보_조회를_요청하여_연결에_성공한다() {
		KakaoLoginRequest kakaoLoginParams = new KakaoLoginRequest();
		kakaoLoginParams.setAccessToken("access_token");
		//연결이 되었을 땐 , 잘못된 accessToken으로 요청을 보냈기 때문에 401 Unauthorized 에러가 발생한다.
		Assertions.assertThrows(InvalidAuthenticationException.class, () -> {
			oAuthInfoService.request(kakaoLoginParams);
		});
	}

	@Test
	void 네이버_사용자_정보_조회를_요청하여_연결에_성공한다() {
		NaverLoginRequest naverLoginRequest = new NaverLoginRequest();
		naverLoginRequest.setAccessToken("access_token");
		naverLoginRequest.getOauthProvider();
		//연결이 되었을 땐 , 잘못된 accessToken으로 요청을 보냈기 때문에 401 Unauthorized 에러가 발생한다.
		Assertions.assertThrows(InvalidAuthenticationException.class, () -> {
			oAuthInfoService.request(naverLoginRequest);
		});
	}

}
