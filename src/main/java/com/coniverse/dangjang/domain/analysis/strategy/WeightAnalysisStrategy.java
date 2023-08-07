package com.coniverse.dangjang.domain.analysis.strategy;

import org.springframework.stereotype.Component;

import com.coniverse.dangjang.domain.analysis.vo.AnalysisData.AbstractAnalysisData;
import com.coniverse.dangjang.domain.analysis.vo.AnalysisData.WeightAnalysisData;
import com.coniverse.dangjang.domain.code.enums.CodeGroup;

/**
 * 체중 분석 전략
 *
 * @author EVE
 * @see WeightAnalysisData
 * @since 1.0.0
 */
@Component
public class WeightAnalysisStrategy implements AnalysisStrategy {
	@Override
	public <T extends AbstractAnalysisData> void analyze(T data) {
		WeightAnalysisData analysisData = (WeightAnalysisData)data;
	}

	/**
	 * 체중 CodeGroup을 반환한다.
	 *
	 * @since 1.0.0
	 */
	@Override
	public CodeGroup getCodeGroup() {
		return CodeGroup.WT;
	}
}
