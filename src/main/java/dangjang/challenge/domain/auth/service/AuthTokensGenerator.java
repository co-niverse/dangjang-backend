package dangjang.challenge.domain.auth.service;

import java.util.Date;

import org.springframework.stereotype.Component;

import dangjang.challenge.domain.auth.dto.AuthToken.AuthTokens;
import lombok.RequiredArgsConstructor;

/**
 * JWT 생성 및 추출
 *
 * @author EVE
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class AuthTokensGenerator {
	private static final String BEARER_TYPE = "Bearer";
	private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;            // 30분
	private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일

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

