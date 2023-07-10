package com.coniverse.dangjang.domain.auth.service.OAuthInfo;

import com.coniverse.dangjang.domain.auth.dto.OAuthProvider;
import com.coniverse.dangjang.domain.user.infrastructure.OAuthInfoResponse;

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
