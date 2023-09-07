package com.coniverse.dangjang.domain.guide.weight.document;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import com.coniverse.dangjang.domain.analysis.enums.Alert;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.guide.common.document.Guide;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Weight Guide Document
 *
 * @author EVE
 * @since 1.0.0
 */
@Getter
@Document
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WeightGuide implements Guide {
	@Id
	@Field(value = "_id", targetType = FieldType.OBJECT_ID)
	private String id;
	private String oauthId;
	private CommonCode type;
	private int weightDiff;
	private Alert alert;
	private LocalDate createdAt;
	private String todayContent;

	@Builder
	private WeightGuide(String oauthId, CommonCode type, int weightDiff, Alert alert, LocalDate createdAt, String todayContent) {
		this.oauthId = oauthId;
		this.type = type;
		this.weightDiff = weightDiff;
		this.alert = alert;
		this.createdAt = createdAt;
		this.todayContent = todayContent;
	}
}
