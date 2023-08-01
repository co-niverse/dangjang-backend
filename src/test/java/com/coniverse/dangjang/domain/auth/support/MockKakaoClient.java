package com.coniverse.dangjang.domain.auth.support;

import static com.coniverse.dangjang.fixture.UserFixture.*;

import com.coniverse.dangjang.domain.auth.dto.OauthProvider;
import com.coniverse.dangjang.domain.infrastructure.auth.client.OAuthClient;
import com.coniverse.dangjang.domain.infrastructure.auth.dto.KakaoInfoResponse;
import com.coniverse.dangjang.domain.infrastructure.auth.dto.OAuthInfoResponse;
import com.coniverse.dangjang.support.annotation.FakeBean;

/**
 * 테스트용 kakao client
 *
 * @author TEO
 * @since 1.0.0
 */
@FakeBean
public class MockKakaoClient implements OAuthClient {
	@Override
	public OauthProvider getOauthProvider() {
		return OauthProvider.KAKAO;
	}

	@Override
	public OAuthInfoResponse requestOauthInfo(String accessToken) {
		return new KakaoInfoResponse(유저_이브().getOauthId());
	}
}
