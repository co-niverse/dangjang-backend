package com.coniverse.dangjang.domain.auth.service;

import static com.coniverse.dangjang.fixture.JwtTokenFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.security.Key;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.coniverse.dangjang.domain.auth.dto.AuthToken;
import com.coniverse.dangjang.domain.user.entity.enums.Role;

import io.jsonwebtoken.Claims;

/**
 * @author EVE
 * @since 1.0.0
 */
@SpringBootTest
class AuthTokenGeneratorTest {
	private final String subject = "12345678";
	@Autowired
	private AuthTokenGenerator authTokenGenerator;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Value("${jwt.access.exp}")
	private long accessTokenExpireTime;

	@Test
	void 토큰을_생성한다() {
		AuthToken authToken = authTokenGenerator.generate(subject, Role.USER); //Todo : role 수정

		assertAll(
			() -> assertThat(authToken.getAccessToken()).isNotNull(),
			() -> assertThat(authToken.getRefreshToken()).isNotNull(),
			() -> assertThat(authToken.getGrantType()).isEqualTo("Bearer"),
			() -> assertThat(authToken.getExpiresIn()).isEqualTo(accessTokenExpireTime / 1000L)
		);
	}

	@Test
	void key를_전달한다() {
		Key key = jwtTokenProvider.getKey();
		assertThat(key).isNotNull();
	}

	@Test
	void parseClaims을_반환한다() throws RuntimeException {
		Key key = jwtTokenProvider.getKey();
		String accessToken = accessToken_생성(key);
		Claims claims = jwtTokenProvider.parseClaims(accessToken);
		//Todo : assertThat 수정 필요
		assertAll(
			() -> assertThat(claims).isNotNull()
		);

	}
}
