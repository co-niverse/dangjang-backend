package com.coniverse.dangjang.global.exception;

/**
 * @author TEO
 * @since 1.0.0
 */
public class BadRequestException extends BusinessException {
	public BadRequestException() {
		super(400, "잘못된 요청입니다.");
	}
}
