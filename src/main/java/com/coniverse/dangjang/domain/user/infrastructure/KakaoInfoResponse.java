package com.coniverse.dangjang.domain.user.infrastructure;

import java.util.Date;

import com.coniverse.dangjang.domain.auth.dto.OauthProvider;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * 카카오 사용자 정보 조회시 필요한 RESPONSE
 *
 * @author Eve
 * @since 1.0
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoInfoResponse implements OAuthInfoResponse {
	private Long id;
	private Date connected_at;

	@Override
	public Long getUserId() {
		return id;
	}

	@Override
	public OauthProvider getOAuthProvider() {
		return OauthProvider.KAKAO;
	}
}

