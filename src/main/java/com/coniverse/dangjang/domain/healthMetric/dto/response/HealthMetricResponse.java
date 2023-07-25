package com.coniverse.dangjang.domain.healthMetric.dto.response;

import java.time.LocalDate;

import com.coniverse.dangjang.domain.healthMetric.enums.HealthMetricCode;
import com.coniverse.dangjang.domain.healthMetric.enums.HealthMetricType;

/**
 * 건강지표 response dto abstract class이다.
 *
 * @author TEO
 * @since 1.0.0
 */
public record HealthMetricResponse(HealthMetricCode healthMetricCode, HealthMetricType healthMetricType, LocalDate createdAt, String unit) {
}
