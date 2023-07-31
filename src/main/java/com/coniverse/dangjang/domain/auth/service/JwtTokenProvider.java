package com.coniverse.dangjang.domain.auth.service;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * JWT 생성 및 반환
 *
 * @author EVE
 * @since 1.0.0
 */
@Component
public class JwtTokenProvider {
	private final Key key;

	public JwtTokenProvider(@Value("${jwt.secret-key}") String secretKey) {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	/**
	 * jwt token을 생성한다
	 *
	 * @param subject   userid
	 * @param expiredAt JWT 유효 기간
	 * @return jwt token
	 * @since 1.0.0
	 */
	public String generate(String subject, Date expiredAt) {
		return Jwts.builder()
			.setSubject(subject)
			.setExpiration(expiredAt)
			.signWith(key, SignatureAlgorithm.HS512)
			.compact();
	}

	/**
	 * user id를 추출한다
	 *
	 * @param accessToken jwt accessToken
	 * @return user id
	 * @since 1.0.0
	 */
	public String extractSubject(String accessToken) {
		Claims claims = parseClaims(accessToken);
		return claims.getSubject();
	}

	/**
	 * JWT 복호화
	 *
	 * @param accessToken jwt accessToken
	 * @return Claims JWT 복호화
	 * @since 1.0.0
	 */
	private Claims parseClaims(String accessToken) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(accessToken)
				.getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}
}