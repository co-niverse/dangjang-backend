package com.coniverse.dangjang.domain.auth.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import lombok.Builder;
import lombok.Getter;

/**
 * RefreshToken
 *
 * @author EVE
 * @since 1.1.0
 */
@Getter
@RedisHash(value = "refreshToken")
public class RefreshToken {
	@Id
	private String accessToken;

	private String refreshToken;
	@TimeToLive
	private long rtkExpirationTime;

	@Builder
	public RefreshToken(String accessToken, String refreshToken, long rtkExpirationTime) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.rtkExpirationTime = rtkExpirationTime;
	}
}
