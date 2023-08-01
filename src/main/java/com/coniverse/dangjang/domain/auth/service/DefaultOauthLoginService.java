package com.coniverse.dangjang.domain.auth.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coniverse.dangjang.domain.auth.dto.AuthToken;
import com.coniverse.dangjang.domain.auth.dto.OauthProvider;
import com.coniverse.dangjang.domain.auth.dto.request.OauthLoginRequest;
import com.coniverse.dangjang.domain.auth.dto.response.LoginResponse;
import com.coniverse.dangjang.domain.infrastructure.auth.client.OAuthClient;
import com.coniverse.dangjang.domain.infrastructure.auth.dto.OAuthInfoResponse;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.service.UserService;

/**
 * oauth 로그인 서비스
 *
 * @author EVE, TEO
 * @since 1.0.0
 */
@Service
@Transactional
public class DefaultOauthLoginService implements OauthLoginService {
	private final AuthTokenGenerator authTokenGenerator;
	private final UserService userService;
	private final Map<OauthProvider, OAuthClient> clients;

	public DefaultOauthLoginService(AuthTokenGenerator authTokenGenerator, UserService userService, List<OAuthClient> clients) {
		this.authTokenGenerator = authTokenGenerator;
		this.userService = userService;
		this.clients = clients.stream().collect(
			Collectors.toUnmodifiableMap(OAuthClient::getOauthProvider, Function.identity())
		);
	}

	/**
	 * @param params 카카오,네이버 accessToken
	 * @return Content 로그인을 성공하면, JWT TOKEN과 사용자 정보(nickname, authID)를 전달한다.
	 * @since 1.0.0
	 */
	public LoginResponse login(OauthLoginRequest params) {
		OAuthInfoResponse oAuthInfoResponse = request(params);
		User user = userService.findUserByOauthId(oAuthInfoResponse.getOauthId());
		AuthToken authToken = authTokenGenerator.generate(user.getOauthId());

		return new LoginResponse(user.getOauthId(), user.getNickname(),
			authToken.getAccessToken(), authToken.getRefreshToken(), authToken.getExpiresIn());
	}

	/**
	 * @param params 카카오,네이버 accessToken을 받아온다.
	 * @return OAuthInfoResponse 카카오, 네이버 사용자 정보
	 * @since 1.0.0
	 */
	@Override
	public OAuthInfoResponse request(OauthLoginRequest params) {
		OAuthClient client = clients.get(params.getOauthProvider());
		String accessToken = params.getAccessToken();
		return client.requestOauthInfo(accessToken);
	}
}