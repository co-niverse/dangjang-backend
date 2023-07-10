package dangjang.challenge.domain.auth.service.OAuthInfo;

import dangjang.challenge.domain.auth.dto.OAuthProvider;
import dangjang.challenge.domain.user.infrastructure.OAuthInfoResponse;

/**
 * 카카오, 네이버 사용자 정보 요청 공통 INTERFACE
 *
 * @author EVE
 * @since 1.0
 */
public interface OAuthApiClient {
	OAuthProvider oAuthProvider();

	OAuthInfoResponse requestOauthInfo(String accessToken);
}
