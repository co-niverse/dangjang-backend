package com.coniverse.dangjang.domain.healthMetric.exception;

import com.coniverse.dangjang.global.exception.BusinessException;

/**
 * 건강지표 타입을 찾을 수 없을 때 발생하는 예외
 *
 * @author TEO
 * @since 1.0.0
 */
public class HealthMetricTypeNotFoundException extends BusinessException {
	public HealthMetricTypeNotFoundException() {
		super(404, "건강지표 타입을 찾을 수 없습니다.");
	}
}
