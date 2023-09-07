package com.coniverse.dangjang.domain.healthmetric.exception;

import com.coniverse.dangjang.global.exception.BusinessException;

/**
 * type과 newType이 같을 때 발생하는 예외
 *
 * @author TEO
 * @since 1.0.0
 */
public class SameTypeException extends BusinessException {
	public SameTypeException() {
		super(400, "type과 newType은 같을 수 없습니다.");
	}
}
