package dangjang.challenge.domain.auth.dto.AuthToken;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * AuthToken 객체
 *
 * @author EVE
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthTokens {
	private String accessToken;
	private String refreshToken;
	private String grantType;
	private Long expiresIn;

	public static AuthTokens of(String accessToken, String refreshToken, String grantType, Long expiresIn) {
		return new AuthTokens(accessToken, refreshToken, grantType, expiresIn);
	}
}