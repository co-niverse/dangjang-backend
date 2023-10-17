package com.coniverse.dangjang.domain.healthmetric.controller;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coniverse.dangjang.domain.healthmetric.dto.HealthMetricLastDateResponse;
import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthMetricPatchRequest;
import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthMetricPostRequest;
import com.coniverse.dangjang.domain.healthmetric.dto.response.HealthMetricChartResponse;
import com.coniverse.dangjang.domain.healthmetric.dto.response.HealthMetricResponse;
import com.coniverse.dangjang.domain.healthmetric.exception.SameTypeException;
import com.coniverse.dangjang.domain.healthmetric.service.HealthMetricChartSearchService;
import com.coniverse.dangjang.domain.healthmetric.service.HealthMetricRegisterService;
import com.coniverse.dangjang.domain.healthmetric.service.HealthMetricSearchService;
import com.coniverse.dangjang.global.dto.SuccessSingleResponse;
import com.coniverse.dangjang.global.validator.ValidLocalDate;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * 건강 지표 등록 controller
 *
 * @author TEO, EVE
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/health-metric")
@RequiredArgsConstructor
@Validated
public class HealthMetricController {
	private final HealthMetricRegisterService healthMetricRegisterService;
	private final HealthMetricChartSearchService healthMetricChartSearchService;
	private final HealthMetricSearchService healthMetricSearchService;

	/**
	 * HTTP POST METHOD
	 *
	 * @since 1.0.0
	 */
	@PostMapping
	public ResponseEntity<SuccessSingleResponse<HealthMetricResponse>> post(@Valid @RequestBody HealthMetricPostRequest postRequest,
		@AuthenticationPrincipal User principal) {
		HealthMetricResponse response = healthMetricRegisterService.register(postRequest, principal.getUsername());
		return ResponseEntity.ok().body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), response));
	}

	/*
	 * HTTP PATCH METHOD
	 *
	 * @since 1.0.0
	 */
	@PatchMapping
	public ResponseEntity<SuccessSingleResponse<HealthMetricResponse>> patch(@Valid @RequestBody HealthMetricPatchRequest patchRequest,
		@AuthenticationPrincipal User principal) {
		if (patchRequest.isSameType()) {
			throw new SameTypeException();
		}
		HealthMetricResponse response = healthMetricRegisterService.update(patchRequest, principal.getUsername());
		return ResponseEntity.ok().body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), response));
	}

	/**
	 * 건강지표 차트 데이터를 조회한다.
	 *
	 * @param startDate 조회 시작 날짜
	 * @param endDate   조회 종료 날짜
	 * @param principal 유저 정보
	 * @return 건강지표 차트 데이터
	 * @since 1.0.0
	 */
	@GetMapping
	public ResponseEntity<SuccessSingleResponse<HealthMetricChartResponse>> getHealthMetrics(@ValidLocalDate @RequestParam String startDate,
		@ValidLocalDate @RequestParam String endDate, @AuthenticationPrincipal User principal) {
		HealthMetricChartResponse response = healthMetricChartSearchService.findHealthMetricChart(principal.getUsername(), LocalDate.parse(startDate),
			LocalDate.parse(endDate));
		return ResponseEntity.ok().body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), response));
	}

	/**
	 * 마지막 건강지표 생성일을 조회한다
	 *
	 * @param principal 유저 정보
	 * @return 유저의 마지막 건강지표 생성일
	 * @since 1.1.0
	 */

	@GetMapping("/lastDate")
	public ResponseEntity<SuccessSingleResponse<HealthMetricLastDateResponse>> getHealthMetricLastDate(@AuthenticationPrincipal User principal) {
		String oauthId = principal.getUsername();
		HealthMetricLastDateResponse response = healthMetricSearchService.findHealthMetricLastDate(oauthId);
		return ResponseEntity.ok(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), response));
	}
}
