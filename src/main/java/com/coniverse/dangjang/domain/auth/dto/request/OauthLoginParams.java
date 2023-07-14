package com.coniverse.dangjang.domain.auth.dto.request;

import com.coniverse.dangjang.domain.auth.dto.OauthProvider;

/**
 * 로그인 parm interface
 *
 * @author EVE
 * @since 1.0
 */
public interface OauthLoginParams {
	//provider 반환
	OauthProvider takeOauthProvider();

	String takeOauthToken();

}

