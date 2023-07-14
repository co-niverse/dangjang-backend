package com.coniverse.dangjang.domain.user.infrastructure;

import com.coniverse.dangjang.domain.auth.dto.OauthProvider;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

/**
 * 네이버 사용자 정보 조회시 필요한 RESPONSE
 *
 * @author Eve
 * @since 1.0
 */
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverInfoResponse implements OAuthInfoResponse {

	@JsonProperty("response")
	private Response response;

	@Getter
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class Response {
		private Long id;
	}

	@Override
	public Long getUserId() {
		return response.id;
	}

	@Override
	public OauthProvider getOAuthProvider() {
		return OauthProvider.NAVER;
	}
}
