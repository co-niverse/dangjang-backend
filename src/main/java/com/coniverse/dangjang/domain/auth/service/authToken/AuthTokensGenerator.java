package com.coniverse.dangjang.domain.auth.service.authToken;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.auth.dto.AuthToken.AuthTokens;

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
	@Value("${jwt.access-token.expire-time}")
	private long ACCESS_TOKEN_EXPIRE_TIME; // 30분
	@Value("${jwt.refresh-token.expire-time}")
	private long REFRESH_TOKEN_EXPIRE_TIME;  // 7일

	private final JwtTokenProvider jwtTokenProvider;

	/**
	 * JWT 생성
	 *
	 * @param memberId 유저 ID
	 * @return AuthTokens JWT TOKEN 반환
	 * @since 1.0
	 */
	public AuthTokens generate(Long memberId) {
		long now = (new Date()).getTime();
		Date accessTokenExpiredAt = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
		Date refreshTokenExpiredAt = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

		String subject = memberId.toString();
		String accessToken = jwtTokenProvider.generate(subject, accessTokenExpiredAt);
		String refreshToken = jwtTokenProvider.generate(subject, refreshTokenExpiredAt);

		return AuthTokens.of(accessToken, refreshToken, BEARER_TYPE, ACCESS_TOKEN_EXPIRE_TIME / 1000L);
	}

	/**
	 * JWT에서 유저 ID 추출
	 *
	 * @param accessToken JWT 토큰
	 * @return Long.valueOf() 유저ID 반환
	 * @since 1.0
	 */
	public Long extractMemberId(String accessToken) {
		return Long.valueOf(jwtTokenProvider.extractSubject(accessToken));
	}
}

