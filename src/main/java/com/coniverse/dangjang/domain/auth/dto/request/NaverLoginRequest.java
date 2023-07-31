package com.coniverse.dangjang.domain.auth.dto.request;

import com.coniverse.dangjang.domain.auth.dto.OauthProvider;

import jakarta.validation.constraints.NotBlank;

/**
 * 네이버 로그인 dto
 *
 * @author EVE, TEO
 * @since 1.0.0
 */
public record NaverLoginRequest(@NotBlank(message = "") String accessToken) implements OauthLoginRequest {
	@Override
	public OauthProvider getOauthProvider() {
		return OauthProvider.NAVER;
	}

	@Override
	public String getOauthToken() {
		return accessToken;
	}
}
