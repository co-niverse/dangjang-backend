package com.coniverse.dangjang.global.exception;

/**
 * FcmToken이 유효하지 않을 때 발생하는 예외
 *
 * @since 1.0.0
 */
public class InvalidFcmTokenException extends BusinessException {
	public InvalidFcmTokenException() {
		super(404, "FCM 토큰이 유효하지 않습니다.");
	}
}
