package com.coniverse.dangjang.domain.auth.service;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.auth.dto.AuthToken.AuthToken;
import com.coniverse.dangjang.domain.auth.dto.Response.LoginResponse;
import com.coniverse.dangjang.domain.auth.dto.request.OauthLoginRequest;
import com.coniverse.dangjang.domain.auth.service.authToken.AuthTokensGenerator;
import com.coniverse.dangjang.domain.user.dto.UserResponse;
import com.coniverse.dangjang.domain.user.entity.enums.Role;
import com.coniverse.dangjang.domain.user.infrastructure.OAuthInfoResponse;
import com.coniverse.dangjang.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

/**
 * @author EVE
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class OauthLoginService {
	private final AuthTokensGenerator authTokensGenerator;
	private final com.coniverse.dangjang.domain.auth.service.OauthInfoService oauthInfoService;
	private final UserService userService;

	/**
	 * @param params 카카오,네이버 accessToken을 받아온다.
	 * @return Content 로그인을 성공하면, JWT TOKEN과 사용자 정보(nickname, authID)를 전달한다.
	 * @since 1.0.0
	 */
	public LoginResponse login(OauthLoginRequest params) {
		OAuthInfoResponse oAuthInfoResponse = oauthInfoService.request(params);
		UserResponse user = userService.findUser(oAuthInfoResponse);
		AuthToken authToken = authTokensGenerator.generate(user.oauthId(), Role.USER); //ToDo: Role.USER -> user.getRole()
		return new LoginResponse(user.nickname(), authToken.getAccessToken(), authToken.getRefreshToken(), false, false);
	}

}