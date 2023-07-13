package com.coniverse.dangjang.domain.auth.service;

import com.coniverse.dangjang.domain.auth.dto.request.OAuthLoginParams;
import com.coniverse.dangjang.domain.user.infrastructure.OAuthInfoResponse;

public interface OAuthInfoService {
	public OAuthInfoResponse request(OAuthLoginParams params);
}
