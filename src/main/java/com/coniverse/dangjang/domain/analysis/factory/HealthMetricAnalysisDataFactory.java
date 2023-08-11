package com.coniverse.dangjang.domain.analysis.factory;

import com.coniverse.dangjang.domain.analysis.dto.healthMetric.HealthMetricAnalysisData;
import com.coniverse.dangjang.domain.analysis.exception.NonAnalyticDataException;
import com.coniverse.dangjang.domain.code.enums.GroupCode;
import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.user.entity.User;

/**
 * 건강지표 분석 데이터를 생성하는 factory
 *
 * @author TEO
 * @since 1.0.0
 */
public abstract class HealthMetricAnalysisDataFactory {
	/**
	 * 건강지표를 분석 데이터를 생성한다.
	 *
	 * @param healthMetric 건강지표
	 * @param user         건강지표 등록 유저
	 * @return 분석 데이터
	 * @since 1.0.0
	 */
	public HealthMetricAnalysisData createAnalysisData(HealthMetric healthMetric, User user) {
		GroupCode groupCode = healthMetric.getGroupCode();
		if (GroupCode.BLOOD_SUGAR.equals(groupCode)) {
			return this.create(healthMetric, user);
		}
		throw new NonAnalyticDataException();
	}

	abstract HealthMetricAnalysisData create(HealthMetric healthMetric, User user);
}
