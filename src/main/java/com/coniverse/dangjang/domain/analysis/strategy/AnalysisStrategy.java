package com.coniverse.dangjang.domain.analysis.strategy;

import com.coniverse.dangjang.domain.analysis.dto.AnalysisData;
import com.coniverse.dangjang.domain.code.enums.GroupCode;
import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;

/**
 * 분석 전략 interface
 *
 * @author TEO
 * @since 1.0.0
 */
public interface AnalysisStrategy { // TODO Domain
	AnalysisData createAnalysisData(HealthMetric healthMetric);

	AnalysisData analyze(AnalysisData analysisData);

	GroupCode getGroupCode();
}
