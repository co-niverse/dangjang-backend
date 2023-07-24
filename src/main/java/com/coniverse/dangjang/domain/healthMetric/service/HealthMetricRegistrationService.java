package com.coniverse.dangjang.domain.healthMetric.service;

import java.time.LocalDate;

import com.coniverse.dangjang.domain.healthMetric.dto.request.HealthMetricPatchRequest;
import com.coniverse.dangjang.domain.healthMetric.dto.request.HealthMetricPostRequest;
import com.coniverse.dangjang.domain.healthMetric.dto.response.HealthMetricResponse;

/**
 * 건강지표 등록 service interface이다.
 *
 * @author TEO
 * @since 1.0.0
 */
public interface HealthMetricRegistrationService {

	/**
	 * 건강지표를 저장한다.
	 *
	 * @since 1.0.0
	 */
	HealthMetricResponse save(HealthMetricPostRequest request, LocalDate createdAt);

	/**
	 * 건강지표를 수정한다.
	 *
	 * @since 1.0.0
	 */
	HealthMetricResponse update(HealthMetricPatchRequest request, LocalDate createdAt);
}
