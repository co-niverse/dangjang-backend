package com.coniverse.dangjang.domain.healthmetric.dto.response;

import java.time.LocalDate;
import java.util.List;

/**
 * 건강지표 차트 응답
 *
 * @author EVE
 * @since 1.0.0
 */
public record HealthMetricChartResponse(LocalDate startDate, LocalDate endDate, List<BloodSugarMinMax> bloodSugars, List<HealthMetricChartData> weights,
										List<HealthMetricChartData> stepCounts, List<HealthMetricChartData> exerciseCalories) {
}
