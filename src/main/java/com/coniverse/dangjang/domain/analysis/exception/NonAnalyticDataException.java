package com.coniverse.dangjang.domain.analysis.exception;

import com.coniverse.dangjang.global.exception.BusinessException;

/**
 * 분석할 수 없는 데이터일 때 발생하는 예외
 *
 * @author TEO
 * @since 1.0.0
 */
public class NonAnalyticDataException extends BusinessException {
	public NonAnalyticDataException() {
		super(400, "분석할 수 없는 데이터입니다.");
	}
}
