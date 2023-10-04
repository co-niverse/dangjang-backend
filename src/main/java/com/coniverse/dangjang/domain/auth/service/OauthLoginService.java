package com.coniverse.dangjang.domain.auth.service;

import com.coniverse.dangjang.domain.auth.dto.AuthToken;
import com.coniverse.dangjang.domain.auth.dto.request.OauthLoginRequest;
import com.coniverse.dangjang.domain.auth.dto.response.LoginResponse;
import com.coniverse.dangjang.domain.infrastructure.auth.dto.OAuthInfoResponse;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author EVE, TEO
 * @since 1.0.0
 */
public interface OauthLoginService {
	LoginResponse login(OauthLoginRequest params);

	AuthToken getAuthToken(String nickname);

	OAuthInfoResponse request(OauthLoginRequest params);

	AuthToken reissueToken(HttpServletRequest request);
}
