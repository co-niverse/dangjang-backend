package com.coniverse.dangjang.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author EVE
 * @since 1.0.0
 */
@Configuration
public class LoginApiConfig {
	/**
	 * 카카오, 네이버 사용자 정보 조회 api에 필요한 restTemplete
	 *
	 * @since 1.0.0
	 */
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
