package com.coniverse.dangjang.global.support.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum JWTStatus {
	OK("토큰이 유효합니다."),
	EXPIRED("만료된 토큰입니다."),
	INVALID("잘못된 토큰입니다.");

	private final String message;

	public String getMessage() {
		return message;
	}
}
