package com.coniverse.dangjang.domain.healthMetric.controller;

import org.hibernate.validator.constraints.Range;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.coniverse.dangjang.domain.healthMetric.dto.request.HealthMetricPatchRequest;
import com.coniverse.dangjang.domain.healthMetric.dto.request.HealthMetricPostRequest;
import com.coniverse.dangjang.domain.healthMetric.dto.response.HealthMetricResponse;
import com.coniverse.dangjang.global.dto.SuccessSingleResponse;

import jakarta.validation.Valid;

/**
 * 건강 지표 등록 controller interface이다.
 *
 * @author TEO
 * @since 1.0.0
 */
@Validated
public interface HealthMetricRegistrationController {
	/**
	 * HTTP POST METHOD
	 * TODO @AuthenticationPrincipal Map<String, Object> principal
	 *
	 * @since 1.0.0
	 */
	ResponseEntity<SuccessSingleResponse<HealthMetricResponse>> post(@Range(min = 1, max = 12) @PathVariable(value = "month") int month,
		@Range(min = 1, max = 31) @PathVariable(name = "day") int day,
		@Valid @RequestBody HealthMetricPostRequest postRequest);

	/**
	 * HTTP PATCH METHOD
	 *
	 * @since 1.0.0
	 */
	ResponseEntity<SuccessSingleResponse<HealthMetricResponse>> patch(@Range(min = 1, max = 12) @PathVariable(value = "month") int month,
		@Range(min = 1, max = 31) @PathVariable(name = "day") int day,
		@Valid @RequestBody HealthMetricPatchRequest patchRequest);
}

