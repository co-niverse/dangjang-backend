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

@Getter
@Document
@NoArgsConstructor
public class ExerciseGuide {
	@Id
	@Field(value = "_id", targetType = FieldType.OBJECT_ID)
	private String id;
	private String oauthId;
	private int needStepByTTS;
	private GuideSign signByTTS;
	private LocalDate createdAt;
	private String content;

	@Builder
	private ExerciseGuide(String oauthId, LocalDate createdAt, int needStepByTTS, String content, GuideSign signByTTS) {
		this.oauthId = oauthId;
		this.createdAt = createdAt;
		this.needStepByTTS = needStepByTTS;
		this.content = content;
		this.signByTTS = signByTTS;
	}
}
