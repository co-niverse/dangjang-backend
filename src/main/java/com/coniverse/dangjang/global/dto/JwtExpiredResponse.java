package com.coniverse.dangjang.global.dto;

/**
 * 만료된 토큰을 재발급해서 전달할 때 사용한다.
 *
 * @author EVE
 * @since 1.0.0
 */
public record JwtExpiredResponse(boolean success, int errorCode, String message) {
	public JwtExpiredResponse() {
		this(true, 0, "토큰이 만료되어 재발급 되었습니다.");
	}
}
