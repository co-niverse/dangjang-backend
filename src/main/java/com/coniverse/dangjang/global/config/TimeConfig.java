package com.coniverse.dangjang.global.config;

import java.util.TimeZone;

import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

/**
 * 서버 시간 동기화
 *
 * @author TEO
 * @since 1.0.0
 */
@Configuration
public class TimeConfig {

	@PostConstruct
	public void setTimeZone() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}
}
