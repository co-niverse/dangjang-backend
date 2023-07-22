package com.coniverse.dangjang.domain.healthMetric.dto.response;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 건강지표 response dto abstract class이다. 건강지표 response dto class는 이 class를 상속받아야 한다.
 *
 * @author TEO
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class HealthMetricResponse {
	private final String healthMetricCode;
	private final String healthMetricType;
	private final LocalDate createdAt;
}
