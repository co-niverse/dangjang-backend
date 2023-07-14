package com.coniverse.dangjang.domain.auth.service;

import com.coniverse.dangjang.domain.auth.dto.request.OauthLoginParam;
import com.coniverse.dangjang.domain.user.infrastructure.OAuthInfoResponse;

public interface OauthInfoService {
	public OAuthInfoResponse request(OauthLoginParam params);
}
