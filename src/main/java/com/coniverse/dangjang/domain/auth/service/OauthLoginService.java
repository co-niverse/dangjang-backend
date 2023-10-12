package com.coniverse.dangjang.domain.auth.service;

import com.coniverse.dangjang.domain.auth.dto.request.OauthLoginRequest;
import com.coniverse.dangjang.domain.auth.dto.response.LoginResponse;
import com.coniverse.dangjang.domain.infrastructure.auth.dto.OAuthInfoResponse;

/**
 * @author EVE, TEO
 * @since 1.0.0
 */
public interface OauthLoginService {
	LoginResponse login(OauthLoginRequest params, String fcmToken);

	String getAuthToken(String nickname);

	OAuthInfoResponse request(OauthLoginRequest params);

	String reissueToken(String header);

	void logout(String accessToken, String fcmToken);

	void validBlackToken(String accessToken);

}
