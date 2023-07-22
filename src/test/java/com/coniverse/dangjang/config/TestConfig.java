package com.coniverse.dangjang.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.coniverse.dangjang.domain.auth.service.OauthInfoService;
import com.coniverse.dangjang.domain.auth.service.TestOauthInfoService;

@TestConfiguration
public class TestConfig {
	@Bean
	public OauthInfoService oauthInfoService() {
		return new TestOauthInfoService();
	}
}
