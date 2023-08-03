package com.coniverse.dangjang.domain.auth.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.coniverse.dangjang.domain.auth.dto.AuthToken;
import com.coniverse.dangjang.domain.user.entity.enums.Role;

import lombok.RequiredArgsConstructor;

/**
 * JWT 생성 및 추출
 *
 * @author EVE
 * @since 1.0.0
 */

@Component
@RequiredArgsConstructor
public class AuthTokenGenerator {
	private static final String BEARER_TYPE = "Bearer";
	@Value("${jwt.access.exp}")
	private long accessTokenExpireTime;
	@Value("${jwt.refresh.exp}")
	private long refreshTokenExpireTime;

	private final JwtTokenProvider jwtTokenProvider;

	/**
	 * auth token을 생성한다
	 *
	 * @param oauthId 유저 id
	 * @return AuthToken jwt token
	 * @since 1.0.0
	 */
	public AuthToken generate(String oauthId, Role role) {
		long now = new Date().getTime();
		Date accessTokenExpiredAt = new Date(now + accessTokenExpireTime);
		Date refreshTokenExpiredAt = new Date(now + refreshTokenExpireTime);

		String subject = oauthId;
		String accessToken = jwtTokenProvider.generate(subject, role.getRole(), accessTokenExpiredAt);
		String refreshToken = jwtTokenProvider.generate(subject, role.getRole(), refreshTokenExpiredAt);

		return AuthToken.of(accessToken, refreshToken, BEARER_TYPE, accessTokenExpireTime / 1000L);
	}
}

