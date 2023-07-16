package com.coniverse.dangjang.domain.auth.dto.request;

import com.coniverse.dangjang.domain.auth.dto.OauthProvider;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 네이버 로그인 parm
 *
 * @author EVE
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class NaverLoginRequest implements OauthLoginRequest {
	private String accessToken;

	@Override
	public OauthProvider getOauthProvider() {
		return OauthProvider.NAVER;
	}

	@Override
	public String getOauthToken() {
		return accessToken;
	}

}
