package dangjang.challenge.domain.auth.dto.request;

import dangjang.challenge.domain.auth.dto.OAuthProvider;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 네이버 로그인 parm
 *
 * @author EVE
 * @since 1.0
 */
@Getter
@NoArgsConstructor
public class NaverLoginParams implements OAuthLoginParams {
	private String accessToken;

	@Override
	public OAuthProvider oAuthProvider() {
		return OAuthProvider.NAVER;
	}

	@Override
	public String oAuthToken() {
		return accessToken;
	}

}
