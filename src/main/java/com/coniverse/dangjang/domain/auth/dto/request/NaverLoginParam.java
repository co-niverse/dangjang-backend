package com.coniverse.dangjang.domain.auth.dto.request;

import com.coniverse.dangjang.domain.auth.dto.OauthProvider;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 네이버 로그인 parm
 *
 * @author EVE
 * @since 1.0
 */
@Getter
@NoArgsConstructor
public class NaverLoginParam implements OauthLoginParam {
	private String accessToken;

	@Override
	public OauthProvider takeOauthProvider() {
		return OauthProvider.NAVER;
	}

	@Override
	public String takeOauthToken() {
		return accessToken;
	}

}
