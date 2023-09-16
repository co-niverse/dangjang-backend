package com.coniverse.dangjang.domain.healthmetric.controller;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coniverse.dangjang.domain.healthmetric.dto.response.HealthMetricChartResponse;
import com.coniverse.dangjang.domain.healthmetric.service.HealthMetricChartSearchService;
import com.coniverse.dangjang.global.dto.SuccessSingleResponse;
import com.coniverse.dangjang.global.validator.ValidLocalDate;

import lombok.RequiredArgsConstructor;

/**
 * 건강지표 차트 controller
 *
 * @author EVE
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/health-metric/chart")
@RequiredArgsConstructor
@Validated
public class HealthMetricChartController {
	private final HealthMetricChartSearchService healthMetricChartSearchService;

	/**
	 * 건강지표 차트 데이터를 조회한다.
	 *
	 * @param startDate 조회 시작 날짜
	 * @param endDate   조회 종료 날짜
	 * @param principal 유저 정보
	 * @return 건강지표 차트 데이터
	 * @author EVE
	 * @since 1.0.0
	 */
	@GetMapping
	public ResponseEntity<SuccessSingleResponse<HealthMetricChartResponse>> getHealthMetrics(@ValidLocalDate @RequestParam String startDate,
		@ValidLocalDate @RequestParam String endDate, @AuthenticationPrincipal User principal) {
		HealthMetricChartResponse response = healthMetricChartSearchService.findHealthMetricChart(principal.getUsername(), LocalDate.parse(startDate),
			LocalDate.parse(endDate));
		return ResponseEntity.ok().body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), response));
	}
}
