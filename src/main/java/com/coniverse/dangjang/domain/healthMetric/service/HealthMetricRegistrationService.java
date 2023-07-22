package com.coniverse.dangjang.domain.healthMetric.service;

import com.coniverse.dangjang.domain.healthMetric.dto.request.HealthMetricRegistrationRequest;
import com.coniverse.dangjang.domain.healthMetric.dto.response.HealthMetricRegistrationResponse;

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
	HealthMetricRegistrationResponse save(HealthMetricRegistrationRequest request);
}
