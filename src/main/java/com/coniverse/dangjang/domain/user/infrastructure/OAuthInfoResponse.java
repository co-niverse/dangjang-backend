package com.coniverse.dangjang.domain.user.infrastructure;

import com.coniverse.dangjang.domain.auth.dto.OauthProvider;

/**
 * 사용자 정보 조회시 필요한 RESPONSE INTERFACE
 *
 * @author Eve
 * @since 1.0
 */
public interface OAuthInfoResponse {
	Long getUserId();

	OauthProvider getOAuthProvider();
}
