package com.coniverse.dangjang.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import redis.embedded.RedisServer;

/**
 * Redis Config
 *
 * @author EVE
 * @since 1.0.0
 */
@Configuration
public class EmbeddedRedisConfig {

	@Value("${spring.data.redis.port}")
	private int redisPort;

	private RedisServer redisServer;

	@PostConstruct
	public void startRedis() throws IOException {
		int port = isRedisRunning() ? findAvailablePort() : redisPort;
		if (isArmArchitecture()) {
			redisServer = new RedisServer(Objects.requireNonNull(getRedisServerExecutable()), port);
		} else {
			redisServer = RedisServer.builder()
				.port(port)
				.build();
		}
		redisServer.start();
	}

	@PreDestroy
	public void stopRedis() {
		redisServer.stop();
	}

	public int findAvailablePort() throws IOException {
		for (int port = 10000; port <= 65535; port++) {
			Process process = executeGrepProcessCommand(port);
			if (!isRunning(process)) {
				return port;
			}
		}
		throw new RuntimeException("유효한 포트를 찾을 수 없습니다.");
	}

	private boolean isRedisRunning() throws IOException {
		return isRunning(executeGrepProcessCommand(redisPort));
	}

	private Process executeGrepProcessCommand(int redisPort) throws IOException {
		String command = String.format("netstat -nat | grep LISTEN|grep %d", redisPort);
		String[] shell = {"/bin/sh", "-c", command};

		return Runtime.getRuntime().exec(shell);
	}

	private boolean isRunning(Process process) {
		String line;
		StringBuilder pidInfo = new StringBuilder();

		try (BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
			while ((line = input.readLine()) != null) {
				pidInfo.append(line);
			}
		} catch (Exception e) {
			throw new RuntimeException("에러");
		}
		return StringUtils.hasText(pidInfo.toString());
	}

	private File getRedisServerExecutable() {
		try {
			return new File("src/test/resources/redis-server-7.2.1-mac-arm64");
		} catch (Exception e) {
			throw new RuntimeException("파일 에러");
		}
	}

	private boolean isArmArchitecture() {
		return System.getProperty("os.arch").contains("aarch64");
	}

}