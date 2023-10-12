package com.coniverse.dangjang.fixture;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * 테스트에서 필요한 JWT 토큰을 반환해주는 메소드를 가진 클래스
 *
 * @author EVE
 * @since 1.0.0
 */
public class JwtTokenFixture {

	static long now = new Date().getTime();
	static Date accessTokenExpiredAt = new Date(now + 1000 * 60 * 60 * 24 * 7);

	public static String accessToken_생성(Key key) {
		return Jwts.builder()
			.setSubject("oauthId")
			.claim("role", "ROLE_" + "USER")
			.setExpiration(accessTokenExpiredAt)
			.signWith(key, SignatureAlgorithm.HS512)
			.compact();

	}

	public static String 기간이_만료된_accessToken_생성(Key key) {
		return Jwts.builder()
			.setSubject("oauthId")
			.claim("role", "ROLE_" + "USER")
			.setExpiration(new Date(now - 1000 * 60 * 60 * 24 * 7))
			.signWith(key, SignatureAlgorithm.HS512)
			.compact();

	}

}
