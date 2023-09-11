package com.coniverse.dangjang.domain.guide.exercise.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.analysis.dto.AnalysisData;
import com.coniverse.dangjang.domain.analysis.dto.healthMetric.ExerciseAnalysisData;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.code.enums.GroupCode;
import com.coniverse.dangjang.domain.guide.common.dto.GuideResponse;
import com.coniverse.dangjang.domain.guide.common.exception.GuideNotFoundException;
import com.coniverse.dangjang.domain.guide.common.service.GuideGenerateService;
import com.coniverse.dangjang.domain.guide.exercise.document.ExerciseGuide;
import com.coniverse.dangjang.domain.guide.exercise.dto.ExerciseCalorie;
import com.coniverse.dangjang.domain.guide.exercise.dto.WalkGuideContent;
import com.coniverse.dangjang.domain.guide.exercise.mapper.ExerciseMapper;
import com.coniverse.dangjang.domain.guide.exercise.repository.ExerciseGuideRepository;

import lombok.RequiredArgsConstructor;

/**
 * 운동 가이드 생성 및 업데이트
 *
 * @author EVE
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class ExerciseGuideGenerateService implements GuideGenerateService {
	private final ExerciseGuideSearchService exerciseGuideSearchService;
	private final ExerciseMapper exerciseMapper;
	private final ExerciseGuideRepository exerciseGuideRepository;

	/**
	 * 운동 가이드를 생성한다.
	 * <p>
	 * 기존에 운동 가이드가 존재하면, 걸음수 또는 운동타입(걸음수 외의 type)에 따라 가이드를 추가한다.
	 * 기존에 운동 가이드가 존재하지 않는다면, 걸음수 또는 운동타입(걸음수 외의 type)에 따라 가이드를 생성한다.
	 *
	 * @param analysisData 운동 분석 데이터
	 * @return 가이드 응답
	 * @since 1.0.0
	 */
	@Override
	public GuideResponse createGuide(AnalysisData analysisData) {
		ExerciseAnalysisData exerciseAnalysisData = (ExerciseAnalysisData)analysisData;
		WalkGuideContent walkGuideContent = new WalkGuideContent(exerciseAnalysisData.getNeedStepByTTS(), exerciseAnalysisData.getNeedStepByLastWeek());

		Optional<ExerciseGuide> exerciseGuide = exerciseGuideSearchService.findByOauthIdAndCreatedAt(exerciseAnalysisData.getOauthId(),
			exerciseAnalysisData.getCreatedAt());
		// 이미 날짜에 해당하는 가이드가 존재
		if (exerciseGuide.isPresent()) {
			ExerciseGuide existExerciseGuide = exerciseGuide.get();
			// 걸음 수 추가
			if (exerciseAnalysisData.getType().equals(CommonCode.STEP_COUNT)) {
				existExerciseGuide.changeAboutWalk(exerciseAnalysisData.needStepByTTS, exerciseAnalysisData.needStepByLastWeek,
					walkGuideContent.getGuideLastWeek(), walkGuideContent.getGuideTTS());
				return exerciseMapper.toResponse(exerciseGuideRepository.save(existExerciseGuide));
			}
			//운동 추가
			ExerciseCalorie exerciseCalorie = new ExerciseCalorie(exerciseAnalysisData.getType(), exerciseAnalysisData.getCalorie());
			existExerciseGuide.changeExerciseCalories(exerciseCalorie);
			return exerciseMapper.toResponse(exerciseGuideRepository.save(existExerciseGuide));
		}
		// 날짜에 해당하는 가이드가 존재하지 않음
		// 새로운 걸음수 가이드 생성
		if (exerciseAnalysisData.getType().equals(CommonCode.STEP_COUNT)) {
			ExerciseGuide newExerciseGuide = exerciseMapper.toDocument(exerciseAnalysisData, walkGuideContent.guideTTS, walkGuideContent.getGuideLastWeek());
			return exerciseMapper.toResponse(exerciseGuideRepository.save(newExerciseGuide));

		}
		//새로운 운동 가이드 생성
		ExerciseCalorie newExerciseCalorie = new ExerciseCalorie(exerciseAnalysisData.getType(), exerciseAnalysisData.getCalorie());
		ExerciseGuide newExerciseGuide = exerciseMapper.toDocument(exerciseAnalysisData, List.of(newExerciseCalorie));
		return exerciseMapper.toResponse(exerciseGuideRepository.save(newExerciseGuide));
	}

	/**
	 * 운동 가이드를 수정한다.
	 * <p>
	 * 수정하는 type이 걸음수라면, 걸음수 가이드에 따라 가이드를 업데이트한다.
	 * 수정하는 type이 운동종류라면 , 운동종류 가이드에 따라 가이드를 업데이트한다.
	 *
	 * @param analysisData 운동 분석 데이터
	 * @return 가이드 응답
	 * @throws GuideNotFoundException 가이드를 찾을 수 없을 때
	 * @since 1.0.0
	 */
	@Override
	public GuideResponse updateGuide(AnalysisData analysisData) {
		ExerciseAnalysisData exerciseAnalysisData = (ExerciseAnalysisData)analysisData;
		Optional<ExerciseGuide> exerciseGuide = exerciseGuideSearchService.findByOauthIdAndCreatedAt(exerciseAnalysisData.getOauthId(),
			exerciseAnalysisData.getCreatedAt());
		if (exerciseGuide.isEmpty()) {
			throw new GuideNotFoundException();
		}
		ExerciseGuide updateExerciseGuide = exerciseGuide.get();
		if (analysisData.getType().equals(CommonCode.STEP_COUNT)) {
			WalkGuideContent walkGuideContent = new WalkGuideContent(exerciseAnalysisData.getNeedStepByTTS(), exerciseAnalysisData.getNeedStepByLastWeek());
			updateExerciseGuide.changeAboutWalk(exerciseAnalysisData.needStepByTTS, exerciseAnalysisData.needStepByLastWeek,
				walkGuideContent.getGuideLastWeek(), walkGuideContent.getGuideTTS());
			return exerciseMapper.toResponse(exerciseGuideRepository.save(updateExerciseGuide));
		}
		//운동 수정
		ExerciseCalorie exerciseCalorie = new ExerciseCalorie(exerciseAnalysisData.getType(), exerciseAnalysisData.getCalorie());
		updateExerciseGuide.changeExerciseCalories(exerciseCalorie);
		return exerciseMapper.toResponse(exerciseGuideRepository.save(updateExerciseGuide));
	}

	@Override
	public GroupCode getGroupCode() {
		return GroupCode.EXERCISE;
	}

}
