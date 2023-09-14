package com.coniverse.dangjang.domain.guide.weight.document;

import org.springframework.data.mongodb.core.mapping.Document;

import com.coniverse.dangjang.domain.analysis.enums.Alert;
import com.coniverse.dangjang.domain.code.enums.CommonCode;

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
public class WeightGuide {
	@Id
	private String id;
	private String oauthId;
	private CommonCode type;
	private int weightDiff;
	private double bmi;
	private Alert alert;
	private String createdAt;
	private String content;
	private String unit;

	@Builder
	private WeightGuide(String oauthId, CommonCode type, int weightDiff, Alert alert, String createdAt, String content, String unit, double bmi) {
		this.oauthId = oauthId;
		this.type = type;
		this.weightDiff = weightDiff;
		this.alert = alert;
		this.createdAt = createdAt;
		this.content = content;
		this.unit = unit;
		this.bmi = bmi;
	}

	/**
	 * 체중 속성을 변경한다.
	 *
	 * @param weightDiff 정상체중까지의 차이
	 * @param alert      체중 경보
	 * @param content    가이드 내용
	 * @since 1.0.0
	 */
	public void changeAboutWeight(int weightDiff, Alert alert, String content, double bmi) {
		this.weightDiff = weightDiff;
		this.alert = alert;
		this.content = content;
		this.bmi = bmi;
	}
}
