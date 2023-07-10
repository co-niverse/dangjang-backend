package dangjang.challenge.domain.auth.service.OAuthInfo;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author EVE
 * @since 1.0
 */
@SpringBootTest
public class KakaoApiClientTest {
	@Autowired
	private KakaoApiClient kakaoApiClient;

	@Test
	public void 카카오_사용자_정보_가져오기_실패() throws Exception {

		assertThrows(Exception.class, () -> {
			kakaoApiClient.requestOauthInfo("");
			;
		});
	}
}
