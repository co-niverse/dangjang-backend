package com.coniverse.dangjang.domain.auth.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.coniverse.dangjang.domain.auth.dto.OauthProvider;
import com.coniverse.dangjang.domain.auth.dto.request.OauthLoginParams;
import com.coniverse.dangjang.domain.auth.service.oauthInfoRequest.OAuthInfoRequestService;
import com.coniverse.dangjang.domain.user.infrastructure.OAuthInfoResponse;

/**
 * @author Eve
 * @since 1.0
 */

@Component
public class ProductOauthInfoService implements OauthInfoService {
	private final Map<OauthProvider, OAuthInfoRequestService> clients;

	public ProductOauthInfoService(List<OAuthInfoRequestService> clients) {
		this.clients = clients.stream().collect(
			Collectors.toUnmodifiableMap(OAuthInfoRequestService::oAuthProvider, Function.identity())
		);
	}

	/**
	 * @param params 카카오,네이버 accessToken을 받아온다.
	 * @return OAuthInfoResponse 카카오, 네이버 사용자 정보
	 * @since 1.0.0
	 */
	@Override
	public OAuthInfoResponse request(OauthLoginParams params) {
		OAuthInfoRequestService client = clients.get(params.takeOauthProvider());
		String accessToken = params.takeOauthToken();
		return client.requestOauthInfo(accessToken);
	}
}