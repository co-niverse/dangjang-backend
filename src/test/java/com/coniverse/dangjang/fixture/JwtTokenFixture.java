package com.coniverse.dangjang.fixture;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * 테스트에서 필요한 JWT 토큰을 반환해주는 메소드를 가진 클래스
 *
 * @author EVE
 * @since 1.0.0
 */
public class JwtTokenFixture {

	private static String secretKey = "happyhappyhappyhappyhappyhappyhappyhappyhappyhappyhappyhappyhappyhappyhappyhappyhappyhappyhappy";
	static byte[] keyBytes = Decoders.BASE64.decode(secretKey);
	private static final Key key = Keys.hmacShaKeyFor(keyBytes);

	private static long 짧은_토큰_만료시간 = -1000L;
	static long now = new Date().getTime();
	static Date accessTokenExpiredAt = new Date(now + 짧은_토큰_만료시간);

	public static String 유효하지_않는_accessToken_생성() {
		return Jwts.builder()
			.setSubject("oauthId")
			.claim("role", "ROLE_" + "USER")
			.setExpiration(accessTokenExpiredAt)
			.signWith(key, SignatureAlgorithm.HS512)
			.compact();

	}
}
