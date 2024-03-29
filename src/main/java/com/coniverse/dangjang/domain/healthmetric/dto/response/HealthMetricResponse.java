package com.coniverse.dangjang.domain.healthmetric.dto.response;

import java.time.LocalDate;

import com.coniverse.dangjang.domain.guide.common.dto.response.GuideResponse;

/**
 * 건강지표 response dto이다.
 *
 * @author TEO
 * @since 1.0.0
 */
public record HealthMetricResponse(String type, LocalDate createdAt, String unit, GuideResponse guide) {
}
