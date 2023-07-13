package com.coniverse.dangjang.domain.healthMetric.exception;

import com.coniverse.dangjang.global.exception.BusinessException;

/**
 * @author TEO
 * @since 1.0.0
 */
public class HealthMetricCodeNotFoundException extends BusinessException {
	public HealthMetricCodeNotFoundException() {
		super(404, "건강지표 코드를 찾을 수 없습니다.");
	}
}
