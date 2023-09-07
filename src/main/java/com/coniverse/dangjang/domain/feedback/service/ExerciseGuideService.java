package com.coniverse.dangjang.domain.feedback.service;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.coniverse.dangjang.domain.analysis.enums.GuideSign;
import com.coniverse.dangjang.domain.analysis.mapper.ExerciseMapper;
import com.coniverse.dangjang.domain.analysis.vo.AnalysisData.ExerciseAnalysisData;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.feedback.document.ExerciseGuide;
import com.coniverse.dangjang.domain.feedback.enums.GuideString;
import com.coniverse.dangjang.domain.feedback.repository.ExerciseGuideRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ExerciseGuideService {
	private final ExerciseMapper exerciseMapper;
	private final ExerciseGuideRepository exerciseGuideRepository;
	private String content;
	private String comparedToLastWeek;

	private void generateGuide(ExerciseAnalysisData exerciseAnalysisData) {

		if (exerciseAnalysisData.getCommonCode().equals(CommonCode.EC_STC)) {
			if (exerciseAnalysisData.signByTTS.equals(GuideSign.positive)) {
				content = String.format("만보보다 %d 걸음 %s", exerciseAnalysisData.needStepByTTS, GuideString.WLK_enough.getGuide());
			} else {
				content = String.format("만보를 걷기 위해 %d 걸음 %s", exerciseAnalysisData.needStepByTTS, GuideString.WLK_needMore.getGuide());
			}
			if (exerciseAnalysisData.needStepByLastWeek > 0) {
				comparedToLastWeek = String.format("지난주 평균 걸음 수보다 %d 걸음 %s", exerciseAnalysisData.needStepByLastWeek, GuideString.LSTWK_needMore.getGuide());
			} else {
				comparedToLastWeek = String.format("지난주 평균 걸음 수보다 %d 걸음 %s", Math.abs(exerciseAnalysisData.needStepByLastWeek),
					GuideString.LSTWK_enough.getGuide());
			}
		}
	}

	public void saveFeedback(ExerciseAnalysisData exerciseAnalysisData) {
		generateGuide(exerciseAnalysisData);
		Optional<ExerciseGuide> exerciseGuide = exerciseGuideRepository.findByGuideId(exerciseAnalysisData.getOauthId(), exerciseAnalysisData.getCreatedAt());
		if (exerciseGuide.isPresent()) {
			ExerciseGuide updateExerciseGuide = exerciseGuide.get();
			if (exerciseAnalysisData.getCommonCode().equals(CommonCode.EC_STC)) {
				exerciseGuideRepository.save(updateExerciseWLK(updateExerciseGuide, exerciseAnalysisData));
			} else {
				try {
					System.out.println("기존 document object id : " + updateExerciseGuide.getId());
					System.out.println("기존 document 바뀐칼로리: " + updateExerciseGuide.getSwimCalorie());
					updateExerciseGuide.setSwimCalorie(exerciseAnalysisData.calorie);
					System.out.println("수정 document object id : " + updateExerciseGuide.getId());
					System.out.println("수정 document 바뀐칼로리: " + updateExerciseGuide.getSwimCalorie());

					ExerciseGuide exerciseGuide1 = exerciseGuideRepository.save(updateExerciseGuide);
				} catch (Exception e) {
					System.out.println("error : " + "수정에서 오류발생");
					System.out.println("error : " + e.getMessage());
				}

			}

		} else {
			System.out.println("isNotpresent : " + exerciseAnalysisData.getCommonCode());
			if (exerciseAnalysisData.getCommonCode().equals(CommonCode.EC_STC)) {
				ExerciseGuide newExerciseGuide = exerciseMapper.toDocument(exerciseAnalysisData, content, comparedToLastWeek);
				exerciseGuideRepository.save(newExerciseGuide);
			} else {
				ExerciseGuide newExerciseGuide = exerciseMapper.toDocument(exerciseAnalysisData, content, comparedToLastWeek);
				exerciseGuideRepository.save(setExerciseCalorie(newExerciseGuide, exerciseAnalysisData));
			}
		}

	}

	private ExerciseGuide setExerciseCalorie(ExerciseGuide exerciseGuide, ExerciseAnalysisData exerciseAnalysisData) {
		switch (exerciseAnalysisData.getCommonCode()) {
			case EC_WLK:
				exerciseGuide.setWalkCalorie(exerciseAnalysisData.calorie);
				break;
			case EC_RN:
				exerciseGuide.setRunCalorie(exerciseAnalysisData.calorie);
				break;
			case EC_SW:
				exerciseGuide.setSwimCalorie(exerciseAnalysisData.calorie);
				break;
			case EC_BIK:
				exerciseGuide.setBikeCalorie(exerciseAnalysisData.calorie);
				break;
			case EC_HIK:
				exerciseGuide.setHikeCalorie(exerciseAnalysisData.calorie);
				break;
			case EC_HT:
				exerciseGuide.setHealthCalorie(exerciseAnalysisData.calorie);
				break;
			default:
				break;
		}
		return exerciseGuide;
	}

	private ExerciseGuide updateExerciseWLK(ExerciseGuide exerciseGuide, ExerciseAnalysisData exerciseAnalysisData) {
		exerciseGuide.setContent(content);
		exerciseGuide.setComparedToLastWeek(comparedToLastWeek);
		exerciseGuide.setNeedStepByTTS(exerciseAnalysisData.needStepByTTS);
		exerciseGuide.setSignByTTS(exerciseAnalysisData.signByTTS);
		exerciseGuide.setNeedStepByLastWeek(exerciseAnalysisData.needStepByLastWeek);
		return exerciseGuide;
	}
}
