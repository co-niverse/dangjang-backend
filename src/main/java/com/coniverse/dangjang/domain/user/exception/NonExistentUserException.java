package com.coniverse.dangjang.domain.user.exception;

import com.coniverse.dangjang.global.exception.BusinessException;

/**
 * 존재하는 유저가 아닐때 발생하는 예외
 *
 * @author EVE
 * @since 1.0.0
 */
public class NonExistentUserException extends BusinessException {
	public NonExistentUserException() {
		super(401, "존재하는 유저가 아닙니다.");
	}
}
