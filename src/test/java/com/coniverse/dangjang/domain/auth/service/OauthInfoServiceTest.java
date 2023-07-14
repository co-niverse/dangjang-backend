package com.coniverse.dangjang.domain.auth.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coniverse.dangjang.domain.auth.dto.request.KakaoLoginParam;

@SpringBootTest
class OauthInfoServiceTest {
	@Autowired
	private ProductOauthInfoService oAuthInfoService;

	@Test
	void accessToken으로_사용자정보_가져오기() {
		KakaoLoginParam kakaoLoginParams = new KakaoLoginParam();
		kakaoLoginParams.setAccessToken("4J-zgwK68lN3RIm8iy1Qv0EGE54mbyOrVc-X1cf1CinJXgAAAYk1SMch");
		kakaoLoginParams.takeOauthProvider();
		Assertions.assertThrows(Exception.class, () -> {
			oAuthInfoService.request(kakaoLoginParams);
		});
	}

}
