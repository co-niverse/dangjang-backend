package com.coniverse.dangjang.domain.healthmetric.exception;

import com.coniverse.dangjang.global.exception.BusinessException;

/**
 * 건강지표 코드를 찾을 수 없을 때 발생하는 예외
 *
 * @author TEO
 * @since 1.0.0
 */
public class HealthMetricCodeNotFoundException extends BusinessException {
	public HealthMetricCodeNotFoundException() {
		super(404, "건강지표 코드를 찾을 수 없습니다.");
	}
}
