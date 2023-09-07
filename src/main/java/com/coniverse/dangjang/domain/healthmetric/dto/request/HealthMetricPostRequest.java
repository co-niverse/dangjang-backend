package com.coniverse.dangjang.domain.healthmetric.dto.request;

import com.coniverse.dangjang.global.validator.LocalDate;

import jakarta.validation.constraints.NotBlank;

/**
 * 건강지표 request post dto이다.
 *
 * @author TEO
 * @since 1.0.0
 */
public record HealthMetricPostRequest(@NotBlank(message = "건강지표 상세 타입은 필수로 입력해야 합니다.") String type,
									  @LocalDate String createdAt,
									  @NotBlank(message = "단위는 필수로 입력해야 합니다.") String unit) {
}
