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
	void 카카오_사용자_정보_조회를_요청하여_연결하여_InvalidAuthenticationException을_던진다() {
		KakaoLoginRequest kakaoLoginParams = new KakaoLoginRequest();
		kakaoLoginParams.setAccessToken("access_token");
		Assertions.assertThrows(InvalidAuthenticationException.class, () -> {
			oAuthInfoService.request(kakaoLoginParams);
		});
	}

	@Test
	void 네이버_사용자_정보_조회를_요청하여_연결하여_InvalidAuthenticationException을_던진다() {
		NaverLoginRequest naverLoginRequest = new NaverLoginRequest();
		naverLoginRequest.setAccessToken("access_token");
		Assertions.assertThrows(InvalidAuthenticationException.class, () -> {
			oAuthInfoService.request(naverLoginRequest);
		});
	}

}
