package com.coniverse.dangjang.domain.auth.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 로그인 RESPONSE
 *
 * @author EVE
 * @since 1.0
 */
@AllArgsConstructor
@Getter
public class LoginResponse {
	private Long oauthId;
	private String nickname;
	private String accessToken;
	private String refreshToken;
	private Long expiresIn;
}
