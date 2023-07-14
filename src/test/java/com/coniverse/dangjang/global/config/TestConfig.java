package com.coniverse.dangjang.global.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.coniverse.dangjang.domain.auth.service.AuthTokensGenerator;
import com.coniverse.dangjang.domain.auth.service.JwtTokenProvider;
import com.coniverse.dangjang.domain.auth.service.OauthLoginService;
import com.coniverse.dangjang.domain.auth.service.TestOauthInfoService;
import com.coniverse.dangjang.domain.user.repository.UserRepository;
import com.coniverse.dangjang.domain.user.service.UserService;

@TestConfiguration
public class TestConfig {
	@Value("${jwt.secret-key}")
	private String secretKey;
	@Autowired
	public UserRepository userRepository;

	@Bean
	public OauthLoginService oAuthLoginService() {
		return new OauthLoginService(new AuthTokensGenerator(new JwtTokenProvider(secretKey)),
			new TestOauthInfoService(userRepository), new UserService(userRepository));
	}

}
