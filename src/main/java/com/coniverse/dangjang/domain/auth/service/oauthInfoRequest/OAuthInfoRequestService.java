package com.coniverse.dangjang.domain.auth.service.oauthInfoRequest;

import com.coniverse.dangjang.domain.auth.dto.OauthProvider;
import com.coniverse.dangjang.domain.user.infrastructure.OAuthInfoResponse;

/**
 * 카카오, 네이버 사용자 정보 요청 공통 INTERFACE
 *
 * @author EVE
 * @since 1.0
 */
public interface OAuthInfoRequestService {
	OauthProvider getOauthProvider();

	OAuthInfoResponse requestOauthInfo(String accessToken);
}
