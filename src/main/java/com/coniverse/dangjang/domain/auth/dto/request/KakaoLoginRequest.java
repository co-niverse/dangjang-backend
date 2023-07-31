package com.coniverse.dangjang.domain.auth.dto.request;

import com.coniverse.dangjang.domain.auth.dto.OauthProvider;

import jakarta.validation.constraints.NotBlank;

/**
 * 카카오 로그인 dto
 *
 * @author EVE, TEO
 * @since 1.0.0
 */
public record KakaoLoginRequest(@NotBlank(message = "") String accessToken) implements OauthLoginRequest {
	@Override
	public OauthProvider getOauthProvider() {
		return OauthProvider.KAKAO;
	}

	@Override
	public String getOauthToken() {
		return accessToken;
	}

}