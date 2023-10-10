package com.coniverse.dangjang.domain.point.exception;

import com.coniverse.dangjang.global.exception.BusinessException;

/**
 * InvalidPointException
 * 포인트 관련 유효하지 않을 때 예외
 *
 * @author EVE
 * @since 1.0.0
 */
public class InvalidPointException extends BusinessException {
	public InvalidPointException(String message) {
		super(404, message);

	}
}
