package com.coniverse.dangjang.domain.auth.service.authToken;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.auth.dto.AuthToken.AuthToken;

import lombok.RequiredArgsConstructor;

/**
 * JWT 생성 및 추출
 *
 * @author EVE
 * @since 1.0
 */

@Service
@RequiredArgsConstructor
public class AuthTokensGenerator {
	private static final String BEARER_TYPE = "Bearer";
	@Value("${jwt.access.exp}")
	private long ACCESS_TOKEN_EXPIRE_TIME;
	@Value("${jwt.refresh.exp}")
	private long REFRESH_TOKEN_EXPIRE_TIME;

	private final JwtTokenProvider jwtTokenProvider;

	/**
	 * JWT 생성
	 *
	 * @param memberId 유저 ID
	 * @return AuthTokens JWT TOKEN 반환
	 * @since 1.0
	 */
	public AuthToken generate(Long memberId) {
		long now = (new Date()).getTime();
		Date accessTokenExpiredAt = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
		Date refreshTokenExpiredAt = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

		String subject = memberId.toString();
		String accessToken = jwtTokenProvider.generate(subject, accessTokenExpiredAt);
		String refreshToken = jwtTokenProvider.generate(subject, refreshTokenExpiredAt);

		return AuthToken.of(accessToken, refreshToken, BEARER_TYPE, ACCESS_TOKEN_EXPIRE_TIME / 1000L);
	}

	/**
	 * JWT에서 유저 ID 추출
	 *
	 * @param accessToken JWT 토큰
	 * @return Long 유저ID 반환
	 * @since 1.0
	 */
	public Long extractMemberId(String accessToken) {
		return Long.valueOf(jwtTokenProvider.extractSubject(accessToken));
	}
}

