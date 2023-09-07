package com.coniverse.dangjang.domain.healthmetric.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthMetricPatchRequest;
import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthMetricPostRequest;
import com.coniverse.dangjang.domain.healthmetric.dto.response.HealthMetricResponse;
import com.coniverse.dangjang.domain.healthmetric.service.HealthMetricRegisterService;
import com.coniverse.dangjang.global.dto.SuccessSingleResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * 건강 지표 등록 controller
 *
 * @author TEO
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/health-metric")
@RequiredArgsConstructor
@Validated
public class HealthMetricRegisterController { // TODO @AuthenticationPrincipal Map<String, Object> principal
	private final HealthMetricRegisterService healthMetricRegisterService;

	/**
	 * HTTP POST METHOD
	 *
	 * @since 1.0.0
	 */
	@PostMapping
	public ResponseEntity<SuccessSingleResponse<HealthMetricResponse>> post(@Valid @RequestBody HealthMetricPostRequest postRequest) {
		HealthMetricResponse response = healthMetricRegisterService.register(postRequest, "11111111");
		return ResponseEntity.ok().body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), response));
	}

	/**
	 * HTTP PATCH METHOD
	 *
	 * @since 1.0.0
	 */
	@PatchMapping
	public ResponseEntity<SuccessSingleResponse<HealthMetricResponse>> patch(@Valid @RequestBody HealthMetricPatchRequest patchRequest) {
		HealthMetricResponse response = healthMetricRegisterService.update(patchRequest, "11111111");
		return ResponseEntity.ok().body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), response));
	}
}
