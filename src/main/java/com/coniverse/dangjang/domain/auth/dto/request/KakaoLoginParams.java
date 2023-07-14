package com.coniverse.dangjang.domain.auth.dto.request;

import com.coniverse.dangjang.domain.auth.dto.OauthProvider;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 카카오 로그인 parm
 *
 * @author EVE
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class KakaoLoginParams implements OauthLoginParams {
	private String accessToken;

	@Override
	public OauthProvider takeOauthProvider() {
		return OauthProvider.KAKAO;
	}

	@Override
	public String takeOauthToken() {
		return accessToken;
	}

}