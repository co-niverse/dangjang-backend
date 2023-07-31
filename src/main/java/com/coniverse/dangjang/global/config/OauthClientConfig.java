package com.coniverse.dangjang.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author EVE, TEO
 * @since 1.0.0
 */
@Configuration
public class OauthClientConfig {
	/**
	 * 카카오, 네이버 사용자 정보 조회 api에 필요한 restTemplate
	 *
	 * @since 1.0.0
	 */
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	} // TODO WebClient

}
