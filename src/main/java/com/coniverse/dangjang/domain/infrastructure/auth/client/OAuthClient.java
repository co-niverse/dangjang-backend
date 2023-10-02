package com.coniverse.dangjang.domain.infrastructure.auth.client;

import com.coniverse.dangjang.domain.auth.dto.OauthProvider;
import com.coniverse.dangjang.domain.infrastructure.auth.dto.OAuthInfoResponse;

/**
 * 카카오, 네이버 사용자 정보 요청 공통 INTERFACE
 *
 * @author EVE
 * @since 1.0.0
 */
public interface OAuthClient {
	OauthProvider getOauthProvider();

	OAuthInfoResponse requestOauthInfo(String accessToken);
}
