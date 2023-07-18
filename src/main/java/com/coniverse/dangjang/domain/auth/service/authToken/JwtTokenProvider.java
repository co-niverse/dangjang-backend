package com.coniverse.dangjang.domain.auth.service.authToken;

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
 * @since 1.0
 */
@Component
public class JwtTokenProvider {

	private final Key key;

	/**
	 * JWT Token Provider
	 *
	 * @param secretKey JWT SECRETKEY
	 * @since 1.0
	 */

	public JwtTokenProvider(@Value("${jwt.secret-key}") String secretKey) {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	/**
	 * JWT Token 생성
	 *
	 * @param subject userID String으로 변환한 값
	 * @return Jwts.builder() JWT TOKEN
	 * @Parm expiredAt JWT 유효 기간
	 * @since 1.0
	 */
	public String generate(String subject, Date expiredAt) {
		return Jwts.builder()
			.setSubject(subject)
			.setExpiration(expiredAt)
			.signWith(key, SignatureAlgorithm.HS512)
			.compact();
	}

	/**
	 * userID 추출
	 *
	 * @param accessToken JWT ACCESSTOKEN
	 * @return claims.getSubject() userID 반환
	 * @since 1.0
	 */
	public String extractSubject(String accessToken) {
		Claims claims = parseClaims(accessToken);
		return claims.getSubject();
	}

	/**
	 * JWT 복호화 , 만료된 Token refresh Token으로 검증 후 재발급
	 *
	 * @param accessToken JWT ACCESSTOKEN
	 * @return claims.getSubject() userID 반환
	 * @since 1.0
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