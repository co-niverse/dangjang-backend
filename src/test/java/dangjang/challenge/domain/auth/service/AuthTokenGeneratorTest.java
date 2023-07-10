package dangjang.challenge.domain.auth.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import dangjang.challenge.domain.auth.dto.AuthToken.AuthTokens;

/**
 * @author EVE
 * @since 1.0
 */
@SpringBootTest
public class AuthTokenGeneratorTest {
	@Autowired
	private AuthTokensGenerator authTokensGenerator;
	@Value("${oauth.kakao.exID}")
	private Long exID;

	@Test
	void 토큰_생성() {

		assertThat(authTokensGenerator.generate(exID)).isNotNull();
	}

	@Test
	void 유저_id_추출() {
		//given
		AuthTokens authTokens = authTokensGenerator.generate(exID);
		assertThat(authTokensGenerator.extractMemberId(authTokens.getAccessToken())).isNotNull();
	}

}
