package com.coniverse.dangjang.domain.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.coniverse.dangjang.domain.auth.dto.AuthToken;
import com.coniverse.dangjang.domain.auth.dto.request.OauthLoginRequest;
import com.coniverse.dangjang.domain.auth.dto.response.LoginResponse;
import com.coniverse.dangjang.domain.infrastructure.auth.dto.KakaoInfoResponse;
import com.coniverse.dangjang.domain.infrastructure.auth.dto.OAuthInfoResponse;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.service.UserService;
import com.coniverse.dangjang.support.annotation.FakeBean;

@FakeBean
@Transactional
public class TestOauthLoginService implements OauthLoginService {
	@Autowired
	private UserService userService;
	@Autowired
	private AuthTokenGenerator authTokenGenerator;

	@Override
	public LoginResponse login(OauthLoginRequest params) {
		OAuthInfoResponse oAuthInfoResponse = request(params);
		User user = userService.findUserByOauthId(oAuthInfoResponse.getOauthId());
		AuthToken authToken = authTokenGenerator.generate(user.getOauthId());

		return new LoginResponse(user.getOauthId(), user.getNickname(),
			authToken.getAccessToken(), authToken.getRefreshToken(), authToken.getExpiresIn());
	}

	@Override
	public OAuthInfoResponse request(OauthLoginRequest params) {
		return new KakaoInfoResponse("22222222");
	}
}
