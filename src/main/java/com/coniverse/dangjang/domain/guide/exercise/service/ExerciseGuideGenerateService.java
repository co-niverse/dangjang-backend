package com.coniverse.dangjang.domain.guide.exercise.service;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.coniverse.dangjang.domain.analysis.dto.AnalysisData;
import com.coniverse.dangjang.domain.analysis.dto.healthMetric.ExerciseAnalysisData;
import com.coniverse.dangjang.domain.analysis.mapper.ExerciseMapper;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.code.enums.GroupCode;
import com.coniverse.dangjang.domain.guide.common.dto.GuideResponse;
import com.coniverse.dangjang.domain.guide.common.service.GuideGenerateService;
import com.coniverse.dangjang.domain.guide.exercise.document.ExerciseGuide;
import com.coniverse.dangjang.domain.guide.exercise.dto.ExerciseCalorie;
import com.coniverse.dangjang.domain.guide.exercise.dto.ExerciseGuideResponse;
import com.coniverse.dangjang.domain.guide.exercise.enums.GuideString;
import com.coniverse.dangjang.domain.guide.exercise.repository.ExerciseGuideRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ExerciseGuideGenerateService implements GuideGenerateService {
	private final ExerciseGuideSearchService exerciseGuideSearchService;
	private final ExerciseMapper exerciseMapper;
	private final ExerciseGuideRepository exerciseGuideRepository;
	private String content;
	private String comparedToLastWeek;

	@Override
	public GuideResponse generateGuide(AnalysisData analysisData) {
		ExerciseAnalysisData exerciseAnalysisData = (ExerciseAnalysisData)analysisData;
		//걸음수
		if (exerciseAnalysisData.getType().equals(CommonCode.STEP_COUNT)) {
			if (exerciseAnalysisData.getUnit() > 0) {
				content = String.format("만보보다 %d 걸음 %s", exerciseAnalysisData.needStepByTTS, GuideString.WLK_enough.getGuide());
			} else {
				content = String.format("만보를 걷기 위해 %d 걸음 %s", exerciseAnalysisData.needStepByTTS, GuideString.WLK_needMore.getGuide());
			}
			if (exerciseAnalysisData.needStepByLastWeek > 0) {
				comparedToLastWeek = String.format("지난주 평균 걸음 수보다 %d 걸음 %s", exerciseAnalysisData.needStepByLastWeek,
					GuideString.LSTWK_enough.getGuide());
			} else {
				comparedToLastWeek = String.format("지난주 평균 걸음 수보다 %d 걸음 %s", Math.abs(exerciseAnalysisData.needStepByLastWeek),
					GuideString.LSTWK_needMore.getGuide());
			}
			ExerciseGuide newExerciseGuide = exerciseMapper.toDocument(exerciseAnalysisData, content, comparedToLastWeek);
			exerciseGuideRepository.save(newExerciseGuide);
			return new ExerciseGuideResponse("", newExerciseGuide.getCreatedAt(), newExerciseGuide.getNeedStepByTTS(), newExerciseGuide.getNeedStepByLastWeek(),
				newExerciseGuide.getComparedToLastWeek(), newExerciseGuide.getContent(), newExerciseGuide.getExerciseCalories());
		}
		//운동
		//기존가이드 조회
		Optional<ExerciseGuide> existExerciseGuide = exerciseGuideSearchService.findByOauthIdAndCreatedAt(exerciseAnalysisData.getOauthId(),
			exerciseAnalysisData.getCreatedAt());
		//운동 가이드 생성 추가
		if (existExerciseGuide.isPresent()) {
			ExerciseGuide exerciseGuide = existExerciseGuide.get();
			ExerciseCalorie exerciseCalorie = new ExerciseCalorie(exerciseAnalysisData.getType(), exerciseAnalysisData.getCalorie());
			exerciseGuide.getExerciseCalories().add(exerciseCalorie);
			return exerciseMapper.toResponse(exerciseGuideRepository.save(exerciseGuide));
		}
		ExerciseCalorie newExerciseCalorie = new ExerciseCalorie(exerciseAnalysisData.getType(), exerciseAnalysisData.getCalorie());
		ExerciseGuide newExerciseGuide = exerciseMapper.toDocument(exerciseAnalysisData, newExerciseCalorie);
		return exerciseMapper.toResponse(exerciseGuideRepository.save(newExerciseGuide));
		//update
	}

	@Override
	public GroupCode getGroupCode() {
		return GroupCode.EXERCISE;
	}
}
