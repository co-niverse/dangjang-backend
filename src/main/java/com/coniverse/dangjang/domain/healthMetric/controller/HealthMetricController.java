package com.coniverse.dangjang.domain.healthMetric.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.coniverse.dangjang.domain.healthMetric.dto.request.HealthMetricRequest;
import com.coniverse.dangjang.domain.healthMetric.dto.response.HealthMetricResponse;
import com.coniverse.dangjang.global.dto.SuccessMultiResponse;
import com.coniverse.dangjang.global.dto.SuccessSingleResponse;

/**
 * 건강 지표 controller interface이다.
 *
 * @author TEO
 * @since 1.0.0
 */
public interface HealthMetricController<RQ extends HealthMetricRequest, RP extends HealthMetricResponse> {
	/**
	 * HTTP POST METHOD
	 * TODO @AuthenticationPrincipal Map<String, Object> principal
	 *
	 * @since 1.0.0
	 */
	ResponseEntity<SuccessSingleResponse<RP>> post(@PathVariable(value = "month") int month, @PathVariable(name = "day") int day, @RequestBody RQ requestBody);

	/**
	 * HTTP PATCH METHOD
	 *
	 * @since 1.0.0
	 */
	ResponseEntity<SuccessSingleResponse<RP>> patch(@PathVariable(value = "month") int month, @PathVariable(name = "day") int day, @RequestBody RQ requestBody);

	/**
	 * HTTP GET METHOD
	 *
	 * @since 1.0.0
	 */
	ResponseEntity<SuccessMultiResponse<RP>> get(@PathVariable(value = "month") int month, @PathVariable(name = "day") int day);

}

