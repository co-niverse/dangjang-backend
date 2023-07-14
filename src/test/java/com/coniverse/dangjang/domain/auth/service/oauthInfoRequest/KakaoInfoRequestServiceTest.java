package com.coniverse.dangjang.domain.auth.service.oauthInfoRequest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;

/**
 * @author EVE
 * @since 1.0
 */
@SpringBootTest
class KakaoInfoRequestServiceTest {
	@Autowired
	private KakaoInfoRequestService kakaoApiClient;

	@Test
	void 잘못된_토큰으로_요청시_사용자_정보_가져오기_실패한다() throws HttpClientErrorException.Unauthorized {

		assertThrows(HttpClientErrorException.Unauthorized.class, () -> {
			kakaoApiClient.requestOauthInfo("");
			;
		});
	}
}
