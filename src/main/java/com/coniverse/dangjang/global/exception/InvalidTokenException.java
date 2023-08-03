package com.coniverse.dangjang.global.exception;

/**
 * @author EVE
 * @since 1.0.0
 */
public class InvalidTokenException extends BusinessException {
	public InvalidTokenException(String message) {
		super(401, message);
	}
}