package com.coniverse.dangjang.domain.auth.dto.request;

import com.coniverse.dangjang.domain.auth.dto.OauthProvider;

/**
 * 로그인 dto interface
 *
 * @author EVE
 * @since 1.0.0
 */
public interface OauthLoginRequest {
	OauthProvider getOauthProvider();

	String getOauthToken();
}
