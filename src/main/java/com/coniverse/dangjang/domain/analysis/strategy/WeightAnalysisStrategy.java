package com.coniverse.dangjang.domain.analysis.strategy;

import org.springframework.stereotype.Component;

import com.coniverse.dangjang.domain.analysis.vo.AnalysisData.AbstractAnalysisData;
import com.coniverse.dangjang.domain.analysis.vo.AnalysisData.WeightAnalysisData;
import com.coniverse.dangjang.domain.code.enums.CodeGroup;
import com.coniverse.dangjang.domain.feedback.service.WeightFeedbackService;

import lombok.RequiredArgsConstructor;

/**
 * 체중 분석 전략
 *
 * @author EVE
 * @see WeightAnalysisData
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class WeightAnalysisStrategy implements AnalysisStrategy {
	private final WeightFeedbackService weightFeedbackService;

	@Override
	public <T extends AbstractAnalysisData> void analyze(T data) {
		WeightAnalysisData analysisData = (WeightAnalysisData)data;
		weightFeedbackService.saveFeedback(analysisData);
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
