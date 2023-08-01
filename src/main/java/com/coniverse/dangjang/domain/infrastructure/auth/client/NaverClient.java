package com.coniverse.dangjang.domain.infrastructure.auth.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.coniverse.dangjang.domain.auth.dto.OauthProvider;
import com.coniverse.dangjang.domain.infrastructure.auth.dto.NaverInfoResponse;
import com.coniverse.dangjang.domain.infrastructure.auth.dto.OAuthInfoResponse;

import lombok.RequiredArgsConstructor;

/**
 * 네이버 사용자 정보 조회
 *
 * @author EVE
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
@Profile("!test")
public class NaverClient implements OAuthClient {

	@Value("${oauth.naver.url.api}")
	private String apiUrl;

	private final RestTemplate restTemplate;

	@Override
	public OauthProvider getOauthProvider() {
		return OauthProvider.NAVER;
	}

	/**
	 * @param accessToken 네이버 accessToken
	 * @return OAuthInfoResponse 네이버 사용자 정보
	 * @since 1.0.0
	 */
	@Override
	public OAuthInfoResponse requestOauthInfo(String accessToken) {
		String url = apiUrl + "/v1/nid/me";

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		httpHeaders.set("Authorization", "Bearer " + accessToken);

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

		HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

		return restTemplate.postForObject(url, request, NaverInfoResponse.class);
	}
}
