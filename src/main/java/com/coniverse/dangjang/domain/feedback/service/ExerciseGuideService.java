package com.coniverse.dangjang.domain.feedback.service;

import org.springframework.stereotype.Component;

import com.coniverse.dangjang.domain.analysis.enums.GuideSign;
import com.coniverse.dangjang.domain.analysis.mapper.ExerciseMapper;
import com.coniverse.dangjang.domain.analysis.vo.AnalysisData.ExerciseAnalysisData;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.feedback.document.ExerciseGuide;
import com.coniverse.dangjang.domain.feedback.repository.ExerciseGuideRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ExerciseGuideService {
	private final ExerciseMapper exerciseMapper;
	private final ExerciseGuideRepository exerciseGuideRepository;

	private String generateSTCGuide(ExerciseAnalysisData exerciseAnalysisData) {
		if (exerciseAnalysisData.signByTTS.equals(GuideSign.positive)) {
			return String.format("만보보다 %d 걸음 더 걸었어요!", exerciseAnalysisData.needStepByTTS);
		} else {
			return String.format("만보를 걷기 위해 %d 만큼 더 걸어야 해요!", exerciseAnalysisData.needStepByTTS);
		}
	}

	public void saveFeedback(ExerciseAnalysisData exerciseAnalysisData) {
		String guide = "";
		if (exerciseAnalysisData.getCommonCode().equals(CommonCode.EC_STC)) {
			guide = generateSTCGuide(exerciseAnalysisData);
		}
		ExerciseGuide exerciseGuide = exerciseMapper.toDocument(exerciseAnalysisData, guide);
		exerciseGuideRepository.save(exerciseGuide);

	}
}
