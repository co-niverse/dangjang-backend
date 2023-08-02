package com.coniverse.dangjang.domain.infrastructure.auth.dto;

import com.coniverse.dangjang.domain.auth.dto.OauthProvider;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Naver 인증 유저 정보
 *
 * @author EVE, TEO
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record NaverInfoResponse(Response response) implements OAuthInfoResponse {
	@Override
	public String getOauthId() {
		return response.id;
	}

	@Override
	public OauthProvider getOauthProvider() {
		return OauthProvider.NAVER;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public record Response(String id) {
	}
}
