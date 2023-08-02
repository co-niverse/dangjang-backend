package com.coniverse.dangjang.domain.auth.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.coniverse.dangjang.domain.auth.dto.AuthToken;

/**
 * @author EVE
 * @since 1.0.0
 */
@SpringBootTest
class AuthTokenGeneratorTest {
	private final String subject = "12345678";
	@Autowired
	private AuthTokenGenerator authTokenGenerator;
	@Value("${jwt.access.exp}")
	private long accessTokenExpireTime;

	@Test
	void 토큰을_생성한다() {
		AuthToken authToken = authTokenGenerator.generate(subject);

		assertAll(
			() -> assertThat(authToken.getAccessToken()).isNotNull(),
			() -> assertThat(authToken.getRefreshToken()).isNotNull(),
			() -> assertThat(authToken.getGrantType()).isEqualTo("Bearer"),
			() -> assertThat(authToken.getExpiresIn()).isEqualTo(accessTokenExpireTime / 1000L)
		);
	}
}
