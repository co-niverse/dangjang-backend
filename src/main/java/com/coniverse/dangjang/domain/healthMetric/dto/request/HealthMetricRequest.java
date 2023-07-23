package com.coniverse.dangjang.domain.healthMetric.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 건강지표 request dto abstract class이다.
 *
 * @author TEO
 * @since 1.0.0
 */
public record HealthMetricRequest(@NotBlank(message = "건강지표 타입은 필수로 입력해야 합니다.") String healthMetricType,
								  @NotNull(message = "건강지표 등록일은 필수로 입력해야 합니다.") LocalDate createdAt,
								  @NotBlank(message = "단위는 필수로 입력해야 합니다.") String unit) {
}
