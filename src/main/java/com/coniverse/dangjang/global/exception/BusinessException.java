package com.coniverse.dangjang.global.exception;

import lombok.Getter;

/**
 * 커스텀 예외를 구현한다.
 *
 * @author TEO
 * @since 1.0.0
 */
@Getter
public class BusinessException extends RuntimeException {
	private final int errorCode;

	public BusinessException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}
}
