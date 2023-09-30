package com.coniverse.dangjang.domain.healthmetric.dto.response;

import java.time.LocalDate;

/**
 * 건강지표 차트 데이터를 담는 객체
 *
 * @author EVE
 * @since 1.0.0
 */
public record HealthMetricChartData(LocalDate date, int unit) {
}
