package com.coniverse.dangjang.domain.guide.exercise.dto;

import java.time.LocalDate;
import java.util.List;

import com.coniverse.dangjang.domain.guide.common.dto.GuideResponse;

/**
 * 운동 가이드 반응
 *
 * @author EVE
 * @since 1.0.0
 */
public record ExerciseGuideResponse(LocalDate createdAt, int needStepByTTS, int needStepByLastWeek, String comparedToLastWeek,
									String content, List<ExerciseCalorie> exerciseCalories) implements GuideResponse {
	@Override
	public String type() {
		return null;
	}
}