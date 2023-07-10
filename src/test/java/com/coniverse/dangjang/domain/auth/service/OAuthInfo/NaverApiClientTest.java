package com.coniverse.dangjang.domain.auth.service.OAuthInfo;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author EVE
 * @since 1.0
 */
@SpringBootTest
public class NaverApiClientTest {
	@Autowired
	private NaverApiClient naverApiClient;

	@Test
	public void 네이버_사용자_정보_가져오기_실패() throws Exception {
		assertThrows(Exception.class, () -> {
			naverApiClient.requestOauthInfo("");
			;
		});
	}
}
