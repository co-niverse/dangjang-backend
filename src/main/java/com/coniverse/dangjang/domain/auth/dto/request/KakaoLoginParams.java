package com.coniverse.dangjang.domain.auth.dto.request;

import com.coniverse.dangjang.domain.auth.dto.OAuthProvider;

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
public class KakaoLoginParams implements OAuthLoginParams {
	private String accessToken;

	@Override
	public OAuthProvider takeOauthProvider() {
		return OAuthProvider.KAKAO;
	}

	@Override
	public String takeOauthToken() {
		return accessToken;
	}

}