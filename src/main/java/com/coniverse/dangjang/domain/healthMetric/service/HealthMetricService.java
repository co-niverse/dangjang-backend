package com.coniverse.dangjang.domain.healthMetric.service;

import com.coniverse.dangjang.domain.healthMetric.dto.request.HealthMetricRequest;
import com.coniverse.dangjang.domain.healthMetric.dto.response.HealthMetricResponse;

/**
 * 건강지표 service interface이다.
 *
 * @author TEO
 * @since 1.0.0
 */
public interface HealthMetricService<RQ extends HealthMetricRequest, RP extends HealthMetricResponse> {

	/**
	 * 건강지표를 저장한다.
	 *
	 * @since 1.0.0
	 */
	RP save(RQ request);
}
