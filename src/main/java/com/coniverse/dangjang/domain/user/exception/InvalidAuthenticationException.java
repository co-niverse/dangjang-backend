package com.coniverse.dangjang.domain.user.exception;

import com.coniverse.dangjang.global.exception.BusinessException;

/**
 * InvalidAuthenticationException
 * 카카오, 네이버 oauth 인증이 유효하지 않을 때 발생한다.
 * ProductOauthInfoService에서 발생
 *
 * @author EVE
 * @since 1.0
 */
public class InvalidAuthenticationException extends BusinessException {
	public InvalidAuthenticationException() {
		super(401, "유효하지 않은 인증입니다.");
	}
}
