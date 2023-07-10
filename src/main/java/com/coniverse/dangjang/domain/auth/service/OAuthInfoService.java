package com.coniverse.dangjang.domain.auth.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.coniverse.dangjang.domain.auth.dto.OAuthProvider;
import com.coniverse.dangjang.domain.auth.dto.request.OAuthLoginParams;
import com.coniverse.dangjang.domain.auth.service.OAuthInfo.OAuthApiClient;
import com.coniverse.dangjang.domain.user.infrastructure.OAuthInfoResponse;

/**
 * @author Eve
 * @since 1.0
 */

@Component
public class OAuthInfoService {
	private final Map<OAuthProvider, OAuthApiClient> clients;

	public OAuthInfoService(List<OAuthApiClient> clients) {
		this.clients = clients.stream().collect(
			Collectors.toUnmodifiableMap(OAuthApiClient::oAuthProvider, Function.identity())
		);
	}

	/**
	 * @param params 카카오,네이버 accessToken을 받아온다.
	 * @return OAuthInfoResponse 카카오, 네이버 사용자 정보
	 * @since 1.0
	 */
	public OAuthInfoResponse request(OAuthLoginParams params) {
		OAuthApiClient client = clients.get(params.oAuthProvider());
		String accessToken = params.oAuthToken();
		return client.requestOauthInfo(accessToken);
	}
}