package com.coniverse.dangjang.domain.auth.exception;

import com.coniverse.dangjang.global.exception.BusinessException;

/**
 * NonExistentUserException
 * 존재하는 유저가 아닐때 발생하는 오류
 * findUser()에서 발생한다.
 *
 * @author EVE
 * @since 1.0
 */
public class InvalidAuthenticationException extends BusinessException {
	public InvalidAuthenticationException() {
		super(401, "유효하지 않은 인증입니다.");
	}
}
