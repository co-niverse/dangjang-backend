package com.coniverse.dangjang.config;

import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * Redis Config
 *
 * @author EVE
 * @since 1.0.0
 */

@Configuration
public class RedisTestContainers {

	private static final String REDIS_DOCKER_IMAGE = "redis:5.0.3-alpine";

	static {
		// 새 컨테이너 생성
		GenericContainer<?> REDIS_CONTAINER =
			new GenericContainer<>(DockerImageName.parse(REDIS_DOCKER_IMAGE))
				.withExposedPorts(6379)
				.withReuse(true);
		//Redis Container 를 실행
		REDIS_CONTAINER.start();

		// 컨테이너에 host, port를 매핑한다.
		System.setProperty("spring.data.redis.host", REDIS_CONTAINER.getHost());
		System.setProperty("spring.data.redis.port", REDIS_CONTAINER.getMappedPort(6379).toString());
	}
}