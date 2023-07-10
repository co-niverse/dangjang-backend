package dangjang.challenge.domain.auth.dto.AuthToken;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * @author EVE
 * @since 1.0
 */
public class AuthTokensTest {
	@Test
	void AuthToken_객체_생성() {
		AuthTokens token = new AuthTokens("accessToken", "refreshToken", "B", 1800L);
		assertThat(token).isNotNull();
	}
}
