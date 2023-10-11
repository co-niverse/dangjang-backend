package com.coniverse.dangjang.global.exception;

/**
 * black된 토큰일 때 예외
 *
 * @author EVE
 * @since 1.0.0
 */
public class BlackTokenException extends BusinessException {
	public BlackTokenException() {
		super(400, "로그아웃 되었습니다.");
	}
}
