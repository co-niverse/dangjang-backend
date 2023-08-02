package com.coniverse.dangjang.domain.auth.service;

import com.coniverse.dangjang.domain.auth.dto.request.OauthLoginRequest;
import com.coniverse.dangjang.domain.auth.dto.response.signInResponse;
import com.coniverse.dangjang.domain.infrastructure.auth.dto.OAuthInfoResponse;

/**
 * @author EVE, TEO
 * @since 1.0.0
 */
public interface OauthLoginService {
	signInResponse login(OauthLoginRequest params);

	OAuthInfoResponse request(OauthLoginRequest params);
}
