package com.coniverse.dangjang.domain.healthmetric.dto.request;

import com.coniverse.dangjang.global.validator.NumberAndPositiveOrZero;
import com.coniverse.dangjang.global.validator.ValidLocalDate;

import jakarta.validation.constraints.NotBlank;

/**
 * 건강지표 request patch dto이다.
 *
 * @author TEO
 * @since 1.0.0
 */
public record HealthMetricPatchRequest(@NotBlank(message = "건강지표 상세 타입은 필수로 입력해야 합니다.") String type, String newType,
									   @ValidLocalDate String createdAt,
									   @NumberAndPositiveOrZero @NotBlank(message = "단위는 필수로 입력해야 합니다.") String unit) {
	public boolean isEmptyNewType() {
		return this.newType == null;
	}

	public boolean isSameType() {
		return this.type.equals(this.newType);
	}
}
