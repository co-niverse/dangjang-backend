package com.coniverse.dangjang.domain.feedback.document;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import com.coniverse.dangjang.domain.analysis.enums.GuideSign;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Document
@NoArgsConstructor
public class ExerciseGuide {
	@Id
	@Field(value = "_id", targetType = FieldType.OBJECT_ID)
	private String id;
	private String oauthId;
	private int needStepByTTS;
	private int needStepByLastWeek;
	private GuideSign signByTTS;
	private LocalDate createdAt;
	private String content;
	private String comparedToLastWeek;
	private int walkCalorie;
	private int hikeCalorie;
	private int runCalorie;
	private int bikeCalorie;
	private int swimCalorie;
	private int healthCalorie;

	@Builder
	private ExerciseGuide(String oauthId, LocalDate createdAt, int needStepByTTS, String content, GuideSign signByTTS, String comparedToLastWeek,
		int needStepByLastWeek) {
		this.oauthId = oauthId;
		this.createdAt = createdAt;
		this.needStepByTTS = needStepByTTS;
		this.content = content;
		this.signByTTS = signByTTS;
		this.comparedToLastWeek = comparedToLastWeek;
		this.needStepByLastWeek = needStepByLastWeek;
		this.walkCalorie = 0;
		this.hikeCalorie = 0;
		this.runCalorie = 0;
		this.bikeCalorie = 0;
		this.swimCalorie = 0;
		this.healthCalorie = 0;
	}
}
