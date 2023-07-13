package com.coniverse.dangjang.domain.auth.service;

import java.util.Date;

import com.coniverse.dangjang.domain.auth.dto.request.OAuthLoginParams;
import com.coniverse.dangjang.domain.user.infrastructure.KakaoInfoResponse;
import com.coniverse.dangjang.domain.user.infrastructure.OAuthInfoResponse;
import com.coniverse.dangjang.domain.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Transactional
public class TestOAuthInfoService implements OAuthInfoService {
	public UserRepository userRepository;

	public TestOAuthInfoService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public OAuthInfoResponse request(OAuthLoginParams params) {

		KakaoInfoResponse kakaoInfoResponse = new KakaoInfoResponse();
		kakaoInfoResponse.setId(5555L);
		kakaoInfoResponse.setConnected_at(new Date());

		return kakaoInfoResponse;
	}
}
