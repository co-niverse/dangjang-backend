package com.coniverse.dangjang.global.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.coniverse.dangjang.domain.auth.service.AuthTokensGenerator;
import com.coniverse.dangjang.domain.auth.service.JwtTokenProvider;
import com.coniverse.dangjang.domain.auth.service.OAuthLoginService;
import com.coniverse.dangjang.domain.auth.service.TestOAuthInfoService;
import com.coniverse.dangjang.domain.user.repository.UserRepository;
import com.coniverse.dangjang.domain.user.service.UserService;

@TestConfiguration
public class TestConfig {
	@Value("${jwt.secret-key}")
	private String secretKey;
	@Autowired
	public UserRepository userRepository;

	@Bean
	public OAuthLoginService oAuthLoginService() {
		return new OAuthLoginService(new AuthTokensGenerator(new JwtTokenProvider(secretKey)),
			new TestOAuthInfoService(userRepository), new UserService(userRepository));
	}

}
