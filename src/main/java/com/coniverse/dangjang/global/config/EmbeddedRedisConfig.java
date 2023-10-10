package com.coniverse.dangjang.global.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import redis.embedded.RedisServer;

/**
 * Redis Config
 *
 * @author EVE
 * @since 1.0.0
 */
@Profile("test")
@Configuration
public class EmbeddedRedisConfig {

	private RedisServer redisServer;

	public EmbeddedRedisConfig(@Value("${spring.data.redis.port}") int port) throws IOException {
		this.redisServer = new RedisServer(port);
	}

	@PostConstruct
	public void startRedis() {
		this.redisServer.start();
	}

	@PreDestroy
	public void stopRedis() {
		this.redisServer.stop();
	}

}