package com.coniverse.dangjang.domain.auth.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import lombok.Builder;

/**
 * blackToken
 *
 * @author EVE
 * @since 1.0.0
 */
@RedisHash(value = "blackToken")
public class BlackToken {
	@Id
	private String token;
	@TimeToLive
	private long expirationTime;

	@Builder
	public BlackToken(String token, long expirationTime) {
		this.token = token;
		this.expirationTime = expirationTime;
	}
}
