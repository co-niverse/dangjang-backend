package com.coniverse.dangjang.domain.auth.support;

import static com.coniverse.dangjang.fixture.UserFixture.*;

import com.coniverse.dangjang.domain.auth.dto.OauthProvider;
import com.coniverse.dangjang.domain.infrastructure.auth.client.OAuthClient;
import com.coniverse.dangjang.domain.infrastructure.auth.dto.NaverInfoResponse;
import com.coniverse.dangjang.domain.infrastructure.auth.dto.OAuthInfoResponse;
import com.coniverse.dangjang.support.annotation.FakeBean;

@FakeBean
public class MockNaverClient implements OAuthClient {

	@Override
	public OauthProvider getOauthProvider() {
		return OauthProvider.NAVER;
	}

	@Override
	public OAuthInfoResponse requestOauthInfo(String accessToken) {
		return new NaverInfoResponse(new NaverInfoResponse.Response(유저_이브().getOauthId()));
	}
}
