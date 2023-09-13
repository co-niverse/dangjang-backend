package com.coniverse.dangjang.global.exception;

/**
 * 서버 에러 예외
 *
 * @author TEO
 * @since 1.0.0
 */
public class ServerErrorException extends RuntimeException {
	public ServerErrorException(String message) {
		super(message);
	}
}
