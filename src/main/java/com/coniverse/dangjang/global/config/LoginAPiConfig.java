package com.coniverse.dangjang.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class LoginAPiConfig {
	//카카오 , 네이버 로그인 restAPi
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	// @Bean
	// public OAuthInfoService oAuthInfoService() {
	// 	return new ProductOAuthInfoService();
	// }
}
