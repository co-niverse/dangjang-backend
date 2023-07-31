package com.coniverse.dangjang.domain.auth.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.coniverse.dangjang.domain.auth.dto.AuthToken;

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
	 * @param subject 유저 id
	 * @return AuthToken jwt token
	 * @since 1.0.0
	 */
	public AuthToken generate(String subject) {
		long now = new Date().getTime();
		Date accessTokenExpiredAt = new Date(now + accessTokenExpireTime);
		Date refreshTokenExpiredAt = new Date(now + refreshTokenExpireTime);

		String accessToken = jwtTokenProvider.generate(subject, accessTokenExpiredAt);
		String refreshToken = jwtTokenProvider.generate(subject, refreshTokenExpiredAt);

		return AuthToken.of(accessToken, refreshToken, BEARER_TYPE, accessTokenExpireTime / 1000L);
	}

	/**
	 * access token에서 유저 id를 추출한다
	 *
	 * @param accessToken JWT 토큰
	 * @return user id
	 * @since 1.0.0
	 */
	public Long extractMemberId(String accessToken) {
		return Long.valueOf(jwtTokenProvider.extractSubject(accessToken));
	}
}

