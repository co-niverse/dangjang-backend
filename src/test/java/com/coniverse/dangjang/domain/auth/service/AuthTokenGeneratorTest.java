package com.coniverse.dangjang.domain.auth.service;

import static com.coniverse.dangjang.fixture.JwtTokenFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;

import com.coniverse.dangjang.domain.auth.dto.AuthToken;
import com.coniverse.dangjang.domain.user.entity.enums.Role;
import com.coniverse.dangjang.global.exception.InvalidTokenException;

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
	void 유효하지_않는_토큰은_예외를_발생시킨다() throws InvalidTokenException { //Todo : merge 후 , 예외 추가
		String authToken = 유효하지_않는_accessToken_생성();
		assertThrows(InvalidTokenException.class, () -> jwtTokenProvider.validationToken(authToken));
	}

	@Test
	void Authentication객체를_생성한다() throws RuntimeException {
		AuthToken authToken = authTokenGenerator.generate(subject, Role.USER);
		Authentication authentication = jwtTokenProvider.getAuthentication(authToken.getAccessToken());
		System.out.println("Authentication : " + authentication + " getPrincipal : " + authentication.getPrincipal());
		//Todo : assertThat 수정 필요
		assertAll(
			() -> assertThat(authentication.getPrincipal()).isNotNull(),
			() -> assertThat(authentication.getCredentials()).isNotNull(),
			() -> assertThat(authentication.getAuthorities()).isNotNull()
		);

	}
}
