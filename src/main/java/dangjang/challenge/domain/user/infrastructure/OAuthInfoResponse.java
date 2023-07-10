package dangjang.challenge.domain.user.infrastructure;

import dangjang.challenge.domain.auth.dto.OAuthProvider;

/**
 * 사용자 정보 조회시 필요한 RESPONSE INTERFACE
 *
 * @author Eve
 * @since 1.0
 */
public interface OAuthInfoResponse {
	Long getUserId();

	OAuthProvider getOAuthProvider();
}
