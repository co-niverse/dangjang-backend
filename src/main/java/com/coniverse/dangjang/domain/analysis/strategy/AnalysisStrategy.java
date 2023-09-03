package com.coniverse.dangjang.domain.analysis.strategy;

import com.coniverse.dangjang.domain.analysis.dto.AnalysisData;
import com.coniverse.dangjang.domain.code.enums.GroupCode;
import com.coniverse.dangjang.domain.guide.common.dto.GuideResponse;

/**
 * 분석 전략 interface
 *
 * @author TEO
 * @since 1.0.0
 */
public interface AnalysisStrategy {
	<T extends AnalysisData> GuideResponse analyze(T analysisData);

	GroupCode getGroupCode();
}
