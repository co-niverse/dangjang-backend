package com.coniverse.dangjang.domain.healthMetric.dto.response;

import java.time.LocalDateTime;

import lombok.Getter;

/**
 * 혈당 response dto class이다.
 *
 * @author TEO
 * @since 1.0.0
 */
@Getter
public class BloodSugarResponse extends HealthMetricResponse {
	private final int unit;

	public BloodSugarResponse(final String healthMetricCode, final String healthMetricType, final LocalDateTime createdAt, final int unit) {
		super(healthMetricCode, healthMetricType, createdAt);
		this.unit = unit;
	}
}
