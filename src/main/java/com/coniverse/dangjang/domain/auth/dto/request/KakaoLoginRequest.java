package com.coniverse.dangjang.domain.auth.dto.request;

import com.coniverse.dangjang.domain.auth.dto.OauthProvider;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 카카오 로그인 parm
 *
 * @author EVE
 * @since 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
public class KakaoLoginRequest implements OauthLoginRequest {
	private String accessToken;

	@Override
	public OauthProvider getOauthProvider() {
		return OauthProvider.KAKAO;
	}

	@Override
	public String getOauthToken() {
		return accessToken;
	}

}