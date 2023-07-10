package com.coniverse.dangjang.domain.auth.dto.request;

import com.coniverse.dangjang.domain.auth.dto.OAuthProvider;

/**
 * 로그인 parm interface
 *
 * @author EVE
 * @since 1.0
 */
public interface OAuthLoginParams {
	//provider 반환
	OAuthProvider oAuthProvider();

	String oAuthToken();

}

