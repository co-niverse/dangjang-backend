package com.coniverse.dangjang.domain.healthMetric.dto.response;

import java.time.LocalDate;

import com.coniverse.dangjang.domain.healthMetric.entity.HealthMetricCode;
import com.coniverse.dangjang.domain.healthMetric.entity.HealthMetricType;

/**
 * 건강지표 response dto abstract class이다. 건강지표 response dto class는 이 class를 상속받아야 한다.
 *
 * @author TEO
 * @since 1.0.0
 */
public record HealthMetricRegistrationResponse(HealthMetricCode healthMetricCode, HealthMetricType healthMetricType, LocalDate createdAt, int unit) {
}
