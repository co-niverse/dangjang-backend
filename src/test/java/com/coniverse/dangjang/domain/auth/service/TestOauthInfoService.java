package com.coniverse.dangjang.domain.auth.service;

import java.util.Date;

import com.coniverse.dangjang.domain.auth.dto.request.OauthLoginRequest;
import com.coniverse.dangjang.domain.user.infrastructure.KakaoInfoResponse;
import com.coniverse.dangjang.domain.user.infrastructure.OAuthInfoResponse;
import com.coniverse.dangjang.support.FakeBean;

@FakeBean
class TestOauthInfoService implements OauthInfoService {
	@Override
	public OAuthInfoResponse request(OauthLoginRequest params) {

		KakaoInfoResponse kakaoInfoResponse = new KakaoInfoResponse();
		kakaoInfoResponse.setId("A1234567");
		kakaoInfoResponse.setConnectedAt(new Date());

		return kakaoInfoResponse;
	}
}
