package com.coniverse.dangjang.domain.guide.exercise.document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import com.coniverse.dangjang.domain.analysis.enums.GuideSign;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.guide.common.document.Guide;
import com.coniverse.dangjang.domain.guide.exercise.dto.ExerciseCalorie;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Document
@NoArgsConstructor
public class ExerciseGuide implements Guide {
	@Id
	@Field(value = "_id", targetType = FieldType.OBJECT_ID)
	private String id;
	private String oauthId;
	private int needStepByTTS;
	private int needStepByLastWeek;
	private LocalDate createdAt;
	private String content;
	private String comparedToLastWeek;
	private List<ExerciseCalorie> exerciseCalories = new ArrayList<>();

	@Builder
	private ExerciseGuide(String oauthId, LocalDate createdAt, int needStepByTTS, String content, GuideSign signByTTS, String comparedToLastWeek,
		int needStepByLastWeek, List<ExerciseCalorie> exerciseCalories) {
		this.oauthId = oauthId;
		this.createdAt = createdAt;
		this.needStepByTTS = needStepByTTS;
		this.content = content;
		this.comparedToLastWeek = comparedToLastWeek;
		this.needStepByLastWeek = needStepByLastWeek;
		this.exerciseCalories = exerciseCalories;
	}

	@Override
	public CommonCode getType() {
		return CommonCode.STEP_COUNT;
	}

	public void addExerciseCalorie(ExerciseCalorie exerciseCalorie) {
		this.exerciseCalories.add(exerciseCalorie);
	}
}
