package com.coniverse.dangjang.domain.healthMetric.exception;

import com.coniverse.dangjang.global.exception.BusinessException;

public class HealthMetricAlreadyExistentException extends BusinessException {
	public HealthMetricAlreadyExistentException() {
		super(400, "이미 존재하는 건강지표입니다.");
	}
}
