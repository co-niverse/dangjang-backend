package com.coniverse.dangjang.domain.guide.exercise.dto;

import java.time.LocalDate;
import java.util.List;

import com.coniverse.dangjang.domain.guide.common.dto.GuideResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;

public record ExerciseGuideResponse(@JsonIgnore String id, LocalDate createdAt, int needStepByTTS, int needStepByLastWeek, String comparedToLastWeek,
									String content, List<ExerciseCalorie> exerciseCalories) implements GuideResponse {
	@Override
	public String type() {
		return null;
	}
}
