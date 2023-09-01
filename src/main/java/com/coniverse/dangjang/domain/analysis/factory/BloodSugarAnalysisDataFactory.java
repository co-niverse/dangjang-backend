package com.coniverse.dangjang.domain.analysis.factory;

import org.springframework.stereotype.Component;

import com.coniverse.dangjang.domain.analysis.dto.healthMetric.BloodSugarAnalysisData;
import com.coniverse.dangjang.domain.analysis.dto.healthMetric.HealthMetricAnalysisData;
import com.coniverse.dangjang.domain.code.enums.GroupCode;
import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;

/**
 * 혈당 분석 데이터를 생성하는 factory
 *
 * @author TEO
 * @since 1.0.0
 */
@Component
public class BloodSugarAnalysisDataFactory implements HealthMetricAnalysisDataFactory {

	@Override
	public HealthMetricAnalysisData create(HealthMetric healthMetric) {
		return new BloodSugarAnalysisData(healthMetric);
	}

	@Override
	public GroupCode getGroupCode() {
		return GroupCode.BLOOD_SUGAR;
	}
}
