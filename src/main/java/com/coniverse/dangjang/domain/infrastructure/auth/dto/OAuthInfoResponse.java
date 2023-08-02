package com.coniverse.dangjang.domain.infrastructure.auth.dto;

import com.coniverse.dangjang.domain.auth.dto.OauthProvider;

/**
 * oauth server에서 인증된 유저의 정보를 받는 dto
 *
 * @author EVE
 * @since 1.0.0
 */
public interface OAuthInfoResponse {
	String getOauthId();

	OauthProvider getOauthProvider();
}
