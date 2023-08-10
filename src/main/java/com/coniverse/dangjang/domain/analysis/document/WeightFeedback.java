package com.coniverse.dangjang.domain.analysis.document;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import com.coniverse.dangjang.domain.analysis.enums.WeightSteps;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Document(collection = "weightFeedbacks")
@NoArgsConstructor
public class WeightFeedback {
	@Id
	@Field(value = "_id", targetType = FieldType.OBJECT_ID)
	private String id;
	private String oauthId;
	private int weightDiff;
	private WeightSteps weightSteps;
	private LocalDate createdAt;
	private String feedback;

	@Builder
	public WeightFeedback(String oauthId, int weightDiff, WeightSteps weightSteps, LocalDate createdAt, String feedback) {
		this.oauthId = oauthId;
		this.weightDiff = weightDiff;
		this.weightSteps = weightSteps;
		this.createdAt = createdAt;
		this.feedback = feedback;
	}

}
