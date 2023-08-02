package com.coniverse.dangjang.domain.healthmetric.exception;

import com.coniverse.dangjang.global.exception.BusinessException;

/**
 * 존재하지 않는 건강지표 타입을 요청할 때 발생하는 예외
 *
 * @author TEO
 * @since 1.0.0
 */
public class HealthMetricTypeNonExistentException extends BusinessException {
	public HealthMetricTypeNonExistentException() {
		super(400, "존재하지 않는 건강지표 타입입니다.");
	}
}
