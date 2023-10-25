package com.coniverse.dangjang.global.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;

/**
 * 분산 서버에서 스케줄러를 사용하기 위함
 *
 * @author EVE
 * @since 1.1.0
 */

@Configuration
public class SchedulerConfiguration {
	@Bean
	public LockProvider lockProvider(DataSource dataSource) {
		return new JdbcTemplateLockProvider((dataSource));
	}

}
