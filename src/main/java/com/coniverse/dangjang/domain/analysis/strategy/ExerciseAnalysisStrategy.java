package com.coniverse.dangjang.domain.analysis.strategy;

import org.springframework.stereotype.Component;

import com.coniverse.dangjang.domain.analysis.vo.AnalysisData.AbstractAnalysisData;
import com.coniverse.dangjang.domain.analysis.vo.AnalysisData.ExerciseAnalysisData;
import com.coniverse.dangjang.domain.code.enums.CodeGroup;
import com.coniverse.dangjang.domain.feedback.service.ExerciseGuideService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ExerciseAnalysisStrategy implements AnalysisStrategy {
	private final ExerciseGuideService exerciseGuideService;

	@Override
	public <T extends AbstractAnalysisData> void analyze(T data) {
		ExerciseAnalysisData analysisData = (ExerciseAnalysisData)data;
		exerciseGuideService.saveFeedback(analysisData);
	}

	@Override
	public CodeGroup getCodeGroup() {
		return CodeGroup.EC;
	}
}
