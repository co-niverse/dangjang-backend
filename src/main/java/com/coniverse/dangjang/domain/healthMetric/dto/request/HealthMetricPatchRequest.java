package com.coniverse.dangjang.domain.healthMetric.dto.request;

import jakarta.validation.constraints.NotBlank;

/**
 * 건강지표 request patch dto이다.
 *
 * @author TEO
 * @since 1.0.0
 */
public record HealthMetricPatchRequest(@NotBlank(message = "이전 건강지표 타입은 필수로 입력해야 합니다.") String prevHealthMetricType,
									   @NotBlank(message = "현재 건강지표 타입은 필수로 입력해야 합니다.") String curHealthMetricType,
									   @NotBlank(message = "단위는 필수로 입력해야 합니다.") String unit) {
}
