package com.coniverse.dangjang.domain.healthmetric.exception;

import com.coniverse.dangjang.global.exception.BusinessException;

/**
 * 생성일자가 올바르지 않을 때 발생하는 예외
 *
 * @author TEO
 * @since 1.0.0
 */
public class IncorrectCreatedAtException extends BusinessException {
	public IncorrectCreatedAtException() {
		super(400, "생성일자가 올바르지 않습니다.");
	}
}
