package com.coniverse.dangjang.domain.healthMetric.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 건강지표 request dto abstract class이다. 건강지표 request dto class는 이 class를 상속받아야 한다.
 *
 * @author TEO
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class HealthMetricRequest {
	@NotBlank(message = "건강지표 타입은 null이거나 공백일 수 없습니다.")
	private final String healthMetricType;
	@NotNull(message = "건강지표 생성일은 null일 수 없습니다.")
	private final LocalDate createdAt;
}
