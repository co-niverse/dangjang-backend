package com.coniverse.dangjang.domain.analysis.exception;

import com.coniverse.dangjang.global.exception.BusinessException;

/**
 * 단위가 0보다 작을 때 발생하는 예외
 *
 * @author TEO
 * @since 1.0.0
 */
public class UnitLessThanZeroException extends BusinessException {
	public UnitLessThanZeroException() {
		super(400, "단위는 0보다 작을 수 없습니다");
	}
}
