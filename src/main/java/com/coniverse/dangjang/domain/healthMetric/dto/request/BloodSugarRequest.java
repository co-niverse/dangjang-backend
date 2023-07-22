package com.coniverse.dangjang.domain.healthMetric.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;

/**
 * 혈당 request dto class이다.
 *
 * @author TEO
 * @since 1.0.0
 */
@Getter
public class BloodSugarRequest extends HealthMetricRequest {
	@PositiveOrZero(message = "혈당 단위는 0보다 작을 수 없습니다.")
	@NotNull(message = "혈당 단위는 null일 수 없습니다.")
	private final Integer unit;

	public BloodSugarRequest(final String healthMetricType, final LocalDateTime createdAt, final int unit) {
		super(healthMetricType, createdAt);
		this.unit = unit;
	}
}
