package com.coniverse.dangjang.domain.auth.service.authToken;

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

import com.coniverse.dangjang.domain.user.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
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
	private final UserRepository userRepository;

	/**
	 * JWT Token Provider
	 *
	 * @param secretKey      JWT SECRETKEY
	 * @param userRepository
	 * @since 1.0
	 */

	public JwtTokenProvider(@Value("${jwt.secret-key}") String secretKey, UserRepository userRepository) {
		this.userRepository = userRepository;
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	/**
	 * JWT Token 생성
	 *
	 * @param oauthId userID String으로 변환한 값
	 * @return Jwts.builder() JWT TOKEN
	 * @Parm expiredAt JWT 유효 기간
	 * @since 1.0
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
		} catch (ExpiredJwtException e) {
			throw new RuntimeException("토큰이 만료되었습니다.");
		} catch (UnsupportedJwtException e) {
			throw new RuntimeException("지원되지 않는 토큰입니다.");
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("토큰이 잘못되었습니다.");
		}
	}

	public Authentication getAuthentication(String token) {
		Claims claims = parseClaims(token);

		String oauthId = claims.getSubject().toString();
		//Todo: 왱 Collection 타입으로 만드는지 이해못함
		//클레임에서 권한 정보 가져오기
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