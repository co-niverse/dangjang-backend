package com.coniverse.dangjang.domain.auth.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.coniverse.dangjang.domain.auth.dto.OauthProvider;
import com.coniverse.dangjang.domain.auth.dto.request.OauthLoginRequest;
import com.coniverse.dangjang.domain.auth.service.oauthInfoRequest.OAuthInfoRequestService;
import com.coniverse.dangjang.domain.user.exception.InvalidAuthenticationException;
import com.coniverse.dangjang.domain.user.infrastructure.OAuthInfoResponse;

/**
 * @author EVE
 * @since 1.0
 */

@Service
public class ProductOauthInfoService implements OauthInfoService {
	private final Map<OauthProvider, OAuthInfoRequestService> clients;

	public ProductOauthInfoService(List<OAuthInfoRequestService> clients) {
		this.clients = clients.stream().collect(
			Collectors.toUnmodifiableMap(OAuthInfoRequestService::getOauthProvider, Function.identity())
		);
	}

	/**
	 * @param params 카카오,네이버 accessToken을 받아온다.
	 * @return OAuthInfoResponse 카카오, 네이버 사용자 정보
	 * @Throws InvalidAuthenticationException 카카오, 네이버 사용자 정보 요청 401 에러 발생
	 * @since 1.0.0
	 */
	@Override
	public OAuthInfoResponse request(OauthLoginRequest params) {
		try {
			System.out.println("params.getOauthProvider() = " + params.getOauthProvider());
			OAuthInfoRequestService client = clients.get(params.getOauthProvider());
			String accessToken = params.getOauthToken();
			return client.requestOauthInfo(accessToken);
		} catch (HttpClientErrorException.Unauthorized e) {
			throw new InvalidAuthenticationException();
		}

	}
}