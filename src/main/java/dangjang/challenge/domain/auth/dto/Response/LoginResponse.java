package dangjang.challenge.domain.auth.dto.Response;

import lombok.Getter;

/**
 * 로그인 RESPONSE
 *
 * @author EVE
 * @since 1.0
 */
@Getter
public class LoginResponse {
	private Long OauthId;
	private String nickname;
	private String accessToken;
	private String refreshToken;
	private Long expiresIn;

	public LoginResponse(Long OauthId, String nickname, String accessToken, String refreshToken, Long expiresIn) {
		this.OauthId = OauthId;
		this.nickname = nickname;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.expiresIn = expiresIn;
	}
}
