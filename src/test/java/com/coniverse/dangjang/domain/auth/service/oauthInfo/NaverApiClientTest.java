package com.coniverse.dangjang.domain.auth.service.oauthInfo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author EVE
 * @since 1.0
 */
@SpringBootTest
class NaverApiClientTest {
	@Autowired
	private NaverApiClient naverApiClient;

	@Test
	void 네이버_사용자_정보_가져오기_실패한다() throws Exception {
		assertThrows(Exception.class, () -> {
			naverApiClient.requestOauthInfo("");
			;
		});
	}
}
