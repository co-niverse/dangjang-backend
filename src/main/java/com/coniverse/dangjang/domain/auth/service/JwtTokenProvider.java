package com.coniverse.dangjang.domain.auth.service;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.coniverse.dangjang.global.exception.InvalidTokenException;
import com.coniverse.dangjang.global.support.enums.JWTStatus;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

/**
 * JWT 생성 및 반환
 *
 * @author EVE
 * @since 1.0.0
 */
@Component
public class JwtTokenProvider {
	private final Key key;
	private static final String BEARER = "Bearer";

	public JwtTokenProvider(@Value("${jwt.secret-key}") String secretKey) {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	/**
	 * jwt token을 생성한다
	 *
	 * @param oauthId   userid
	 * @param expiredAt JWT 유효 기간
	 * @return jwt token
	 * @since 1.0.0
	 */
	public String generate(String oauthId, String role, Date expiredAt) {
		return Jwts.builder()
			.setSubject(oauthId)
			.claim("role", "ROLE_" + role)
			.setExpiration(expiredAt)
			.signWith(key, SignatureAlgorithm.HS512)
			.compact();
	}

	public Key getKey() {
		return key;
	}

	/**
	 * 복호화해서 Claims 반환
	 *
	 * @param accessToken JWT ACCESSTOKEN
	 * @return claims.getSubject() userID 반환
	 * @since 1.0.0
	 */
	public Claims parseClaims(String accessToken) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(accessToken)
				.getBody();
		} catch (ExpiredJwtException e) {
			throw new InvalidTokenException("만료된 토큰입니다.");
		}
	}

	/**
	 * 토큰 값을 분리
	 *
	 * @param authHeader
	 * @return 토큰 값
	 * @since 1.0.0
	 */

	public String getToken(final String authHeader) {
		if (StringUtils.hasLength(authHeader) && authHeader.startsWith(BEARER)) {
			return authHeader.substring(7); //TODO JWT Bearer 스페이스바 수정
		} else {
			throw new InvalidTokenException("잘못된 Authorization Header 입니다.");
		}

	}

	/**
	 * token 유효성을 검사한다.
	 * <p>
	 * ExpiredJwtException : 토큰이 만료되었을 때
	 * UnsupportedJwtException : 예상된 형식과 일치하지 않는 JWT 구성
	 * MalformedJwtException : JWT가 올바르게 구성되지 않음(변조 가능성)
	 * SignatureException : JWT 기존 서명을 확인할 수 없을 때
	 * IllegalArgumentException : claims이 비어있을 때
	 *
	 * @param token
	 * @return boolean 토큰 유효성 확인
	 * @since 1.0.0
	 */
	public JWTStatus validationToken(String token) {
		try {
			Key key = getKey();
			Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token);
			return JWTStatus.OK;
		} catch (ExpiredJwtException e) {
			return JWTStatus.EXPIRED;
		} catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
			return JWTStatus.INVALID;
		}
	}
}