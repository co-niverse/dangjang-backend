package com.coniverse.dangjang.domain.auth.service;

import static com.coniverse.dangjang.fixture.JwtTokenFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.security.Key;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coniverse.dangjang.global.exception.InvalidTokenException;

/**
 * JwtTokenProvider 예외 테스트
 *
 * @author EVE
 * @since 1.0.0
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JwtTokenProviderTest {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Test
	void 복호화_토큰이_만료됐다면_예외를_반환한다() {
		Key key = jwtTokenProvider.getKey();
		String 만료된_토큰 = 기간이_만료된_accessToken_생성(key);
		assertThatThrownBy(() -> jwtTokenProvider.parseClaims(만료된_토큰))
			.isInstanceOf(InvalidTokenException.class)
			.hasMessageContaining("만료된 토큰입니다.");
	}

	@Test
	void 토큰_추출중_잘못된_양식이라면_예외를_반환한다() {
		String header = " Bearer testToken";
		assertThatThrownBy(() -> jwtTokenProvider.getToken(header))
			.isInstanceOf(InvalidTokenException.class)
			.hasMessageContaining("잘못된 Authorization Header 입니다.");
	}
}
