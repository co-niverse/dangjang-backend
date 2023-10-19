package com.coniverse.dangjang.domain.guide.exercise.dto;

import java.util.List;

import com.coniverse.dangjang.domain.guide.common.dto.response.GuideResponse;
import com.coniverse.dangjang.domain.guide.exercise.document.ExerciseCalorie;

/**
 * 운동 가이드 응답 dto
 *
 * @author EVE
 * @since 1.0.0
 */
public record ExerciseGuideResponse(String createdAt, int needStepByTTS, int needStepByLastWeek, String comparedToLastWeek,
									String content, int stepsCount, List<ExerciseCalorie> exerciseCalories) implements GuideResponse {
	@Override
	public String type() { // TODO 수정
		return null;
	}

	@Override
	public String title() { // TODO 수정
		return null;
	}
}
