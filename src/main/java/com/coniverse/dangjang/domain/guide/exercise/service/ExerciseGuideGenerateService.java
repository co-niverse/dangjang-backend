package com.coniverse.dangjang.domain.guide.exercise.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.coniverse.dangjang.domain.analysis.dto.AnalysisData;
import com.coniverse.dangjang.domain.analysis.dto.healthMetric.ExerciseAnalysisData;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.code.enums.GroupCode;
import com.coniverse.dangjang.domain.guide.common.dto.GuideResponse;
import com.coniverse.dangjang.domain.guide.common.exception.GuideNotFoundException;
import com.coniverse.dangjang.domain.guide.common.service.GuideGenerateService;
import com.coniverse.dangjang.domain.guide.exercise.document.ExerciseGuide;
import com.coniverse.dangjang.domain.guide.exercise.dto.ExerciseCalorie;
import com.coniverse.dangjang.domain.guide.exercise.enums.GuideString;
import com.coniverse.dangjang.domain.guide.exercise.mapper.ExerciseMapper;
import com.coniverse.dangjang.domain.guide.exercise.repository.ExerciseGuideRepository;

import lombok.RequiredArgsConstructor;

/**
 * 운동 가이드 생성 및 업데이트
 *
 * @author EVE
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class ExerciseGuideGenerateService implements GuideGenerateService {
	private final ExerciseGuideSearchService exerciseGuideSearchService;
	private final ExerciseMapper exerciseMapper;
	private final ExerciseGuideRepository exerciseGuideRepository;
	private String content;
	private String comparedToLastWeek;

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

		Optional<ExerciseGuide> existExerciseGuide = exerciseGuideSearchService.findByOauthIdAndCreatedAt(exerciseAnalysisData.getOauthId(),
			exerciseAnalysisData.getCreatedAt());
		// 이미 날짜에 해당하는 가이드가 존재
		if (existExerciseGuide.isPresent()) {
			// 걸음 수 추가
			if (exerciseAnalysisData.getType().equals(CommonCode.STEP_COUNT)) {
				createContent_STC_CNT(exerciseAnalysisData);
				ExerciseGuide newExerciseGuide = updateNewGuideSTC_CNT(existExerciseGuide.get(), exerciseAnalysisData);
				newExerciseGuide.setId(existExerciseGuide.get().getId());
				return exerciseMapper.toResponse(exerciseGuideRepository.save(newExerciseGuide));

			}
			//운동 추가
			ExerciseGuide exerciseGuide = existExerciseGuide.get();
			ExerciseGuide newExerciseGuide = updateExerciseCalorie(exerciseGuide, exerciseAnalysisData);
			newExerciseGuide.setId(exerciseGuide.getId());
			return exerciseMapper.toResponse(exerciseGuideRepository.save(newExerciseGuide));
		}
		// 날짜에 해당하는 가이드가 존재하지 않음
		// 새로운 걸음수 가이드 생성
		if (exerciseAnalysisData.getType().equals(CommonCode.STEP_COUNT)) {
			createContent_STC_CNT(exerciseAnalysisData);
			ExerciseGuide newExerciseGuide = exerciseMapper.toDocument(exerciseAnalysisData, content, comparedToLastWeek);
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
	 * @since 1.0.0
	 */
	@Override
	public GuideResponse updateGuide(AnalysisData analysisData) {
		ExerciseAnalysisData exerciseAnalysisData = (ExerciseAnalysisData)analysisData;
		Optional<ExerciseGuide> existExerciseGuide = exerciseGuideSearchService.findByOauthIdAndCreatedAt(exerciseAnalysisData.getOauthId(),
			exerciseAnalysisData.getCreatedAt());
		if (existExerciseGuide.isEmpty()) {
			throw new GuideNotFoundException();
		}
		if (analysisData.getType().equals(CommonCode.STEP_COUNT)) {
			createContent_STC_CNT(exerciseAnalysisData);
			ExerciseGuide newExerciseGuide = updateNewGuideSTC_CNT(existExerciseGuide.get(), exerciseAnalysisData);
			newExerciseGuide.setId(existExerciseGuide.get().getId());
			return exerciseMapper.toResponse(exerciseGuideRepository.save(newExerciseGuide));
		}
		//운동 수정
		ExerciseGuide newExerciseGuide = updateExerciseCalorie(existExerciseGuide.get(), exerciseAnalysisData);
		newExerciseGuide.setId(existExerciseGuide.get().getId());
		return exerciseMapper.toResponse(exerciseGuideRepository.save(newExerciseGuide));
	}

	@Override
	public GuideResponse updateGuideWithType(AnalysisData analysisData, CommonCode prevType) {
		return GuideGenerateService.super.updateGuideWithType(analysisData, prevType);
	}

	@Override
	public GroupCode getGroupCode() {
		return GroupCode.EXERCISE;
	}

	/**
	 * 수정된 운동 칼로리를 업데이트한다.
	 * <p>
	 * 기존에 존재하는 운동 칼로리를 삭제하고, 새로운 운동 칼로리를 추가한다.
	 *
	 * @param existGuide 수정될 기존 가이드
	 * @param data       분석한 운동 데이터
	 * @return 운동 가이드
	 * @since 1.0.0
	 */
	public ExerciseGuide updateExerciseCalorie(ExerciseGuide existGuide, ExerciseAnalysisData data) {
		ExerciseCalorie exerciseCalorie = new ExerciseCalorie(data.getType(), data.getCalorie());
		List<ExerciseCalorie> exerciseCalories = existGuide.getExerciseCalories();
		for (ExerciseCalorie existExerciseCalorie : exerciseCalories) {
			if (existExerciseCalorie.type().equals(data.getType())) {
				exerciseCalories.remove(existExerciseCalorie);
				break;
			}
		}
		exerciseCalories.add(exerciseCalorie);
		return existGuide.toBuilder()
			.exerciseCalories(exerciseCalories)
			.build();
	}

	/**
	 * 걸음수에 대한 가이드를 수정한다.
	 *
	 * @param existGuide 수정될 기존 가이드
	 * @param data       분석한 운동 데이터
	 * @return 운동 가이드
	 * @since 1.0.0
	 */
	public ExerciseGuide updateNewGuideSTC_CNT(ExerciseGuide existGuide, ExerciseAnalysisData data) {
		return existGuide.toBuilder()
			.content(content)
			.needStepByLastWeek(data.getNeedStepByLastWeek())
			.needStepByTTS(data.getNeedStepByTTS())
			.comparedToLastWeek(comparedToLastWeek)
			.build();
	}

	/**
	 * 걸음수에 대한 가이드 내용을 생성한다.
	 * <p>
	 * 만보보다 많이 걸었는지 , 저번주 대비 얼마나 걸었는지에 대해 String으로 생성한다.
	 *
	 * @param data 분석한 운동 데이터
	 * @since 1.0.0
	 */

	public void createContent_STC_CNT(ExerciseAnalysisData data) {
		if (data.getNeedStepByTTS() > 0) {
			content = String.format("만보보다 %d 걸음 %s", data.needStepByTTS, GuideString.WLK_enough.getGuide());
		} else if (data.getNeedStepByTTS() == 0) {
			content = "와우! 만보를 걸었어요";
		} else {
			content = String.format("만보를 걷기 위해 %d 걸음 %s", data.needStepByTTS, GuideString.WLK_needMore.getGuide());
		}
		if (data.needStepByLastWeek > 0) {
			comparedToLastWeek = String.format("지난주 평균 걸음 수보다 %d 걸음 %s", data.needStepByLastWeek,
				GuideString.LSTWK_enough.getGuide());
		} else if (data.needStepByLastWeek == 0) {
			comparedToLastWeek = "지난주와 동일하게 걸었어요~ 조금 더 걸어보는건 어때요?";
		} else {
			comparedToLastWeek = String.format("지난주 평균 걸음 수보다 %d 걸음 %s", Math.abs(data.needStepByLastWeek),
				GuideString.LSTWK_needMore.getGuide());
		}
	}
}
