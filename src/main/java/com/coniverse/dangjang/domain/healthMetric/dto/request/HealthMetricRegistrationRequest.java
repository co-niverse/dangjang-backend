package com.coniverse.dangjang.domain.healthMetric.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * 건강지표 request dto abstract class이다.
 *
 * @author TEO
 * @since 1.0.0
 */
public record HealthMetricRegistrationRequest(@NotBlank(message = "건강지표 타입은 null이거나 공백일 수 없습니다.")
											  String healthMetricType,
											  @NotNull(message = "건강지표 생성일은 null일 수 없습니다.")
											  LocalDate createdAt,
											  @PositiveOrZero(message = "단위는 0보다 작을 수 없습니다.")
											  @NotNull(message = "단위는 null일 수 없습니다.")
											  Integer unit) {
}
