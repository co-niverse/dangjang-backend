package com.coniverse.dangjang.domain.auth.service;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.coniverse.dangjang.global.exception.InvalidTokenException;

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

	public boolean validationToken(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
			throw new InvalidTokenException(e.getMessage());
		}
	}

	public Authentication getAuthentication(String token) {
		Claims claims = parseClaims(token);

		String oauthId = claims.getSubject().toString();

		final Collection<? extends GrantedAuthority> authorities = Stream.of(
				claims.get("role").toString())
			.map(SimpleGrantedAuthority::new)
			.toList();

		User principal = new User(claims.getSubject(), oauthId, authorities);

		return new UsernamePasswordAuthenticationToken(principal, oauthId, authorities);
	}

	/**
	 * JWT 복호화 , 만료된 Token refresh Token으로 검증 후 재발급
	 *
	 * @param accessToken JWT ACCESSTOKEN
	 * @return claims.getSubject() userID 반환
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