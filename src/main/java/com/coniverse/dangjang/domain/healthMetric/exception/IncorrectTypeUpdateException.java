package com.coniverse.dangjang.domain.healthMetric.exception;

import com.coniverse.dangjang.global.exception.BusinessException;

/**
 * @author TEO
 * @since 1.0.0
 */
public class IncorrectTypeUpdateException extends BusinessException {
	public IncorrectTypeUpdateException() {
		super(400, "올바르지 않은 타입 변경입니다.");
	}
}
