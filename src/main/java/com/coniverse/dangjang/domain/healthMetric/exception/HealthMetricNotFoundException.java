package com.coniverse.dangjang.domain.healthMetric.exception;

import com.coniverse.dangjang.global.exception.BusinessException;

/**
 * 유저의 건강지표를 찾을 수 없을 때 발생하는 예외
 *
 * @author TEO
 * @since 1.0.0
 */
public class HealthMetricNotFoundException extends BusinessException {
	public HealthMetricNotFoundException() {
		super(404, "해당 건강지표를 찾을 수 없습니다.");
	}
}
