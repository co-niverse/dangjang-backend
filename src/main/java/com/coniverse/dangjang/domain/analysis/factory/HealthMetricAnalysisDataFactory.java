package com.coniverse.dangjang.domain.analysis.factory;

import com.coniverse.dangjang.domain.analysis.dto.healthMetric.HealthMetricAnalysisData;
import com.coniverse.dangjang.domain.code.enums.GroupCode;
import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;

/**
 * 건강지표 분석 데이터를 생성하는 factory
 *
 * @author TEO
 * @since 1.0.0
 */
public interface HealthMetricAnalysisDataFactory {
	/**
	 * 건강지표를 분석 데이터를 생성한다.
	 *
	 * @param healthMetric 건강지표
	 * @return 분석 데이터
	 * @since 1.0.0
	 */
	HealthMetricAnalysisData create(HealthMetric healthMetric);

	GroupCode getGroupCode();
}
