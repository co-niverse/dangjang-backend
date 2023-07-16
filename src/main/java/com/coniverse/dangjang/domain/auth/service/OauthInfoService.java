package com.coniverse.dangjang.domain.auth.service;

import com.coniverse.dangjang.domain.auth.dto.request.OauthLoginRequest;
import com.coniverse.dangjang.domain.user.infrastructure.OAuthInfoResponse;

public interface OauthInfoService {
	public OAuthInfoResponse request(OauthLoginRequest params);
}
