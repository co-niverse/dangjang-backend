package com.coniverse.dangjang.domain.auth.service;

import java.util.Date;

import com.coniverse.dangjang.domain.auth.dto.request.OauthLoginParams;
import com.coniverse.dangjang.domain.user.infrastructure.KakaoInfoResponse;
import com.coniverse.dangjang.domain.user.infrastructure.OAuthInfoResponse;
import com.coniverse.dangjang.domain.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Transactional
public class TestOauthInfoService implements OauthInfoService {
	public UserRepository userRepository;

	public TestOauthInfoService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public OAuthInfoResponse request(OauthLoginParams params) {

		KakaoInfoResponse kakaoInfoResponse = new KakaoInfoResponse();
		kakaoInfoResponse.setId(5555L);
		kakaoInfoResponse.setConnected_at(new Date());

		return kakaoInfoResponse;
	}
}
