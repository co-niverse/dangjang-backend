package com.coniverse.dangjang.domain.guide.bloodsugar.document;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;

import com.coniverse.dangjang.domain.analysis.dto.healthMetric.BloodSugarAnalysisData;
import com.coniverse.dangjang.domain.analysis.enums.Alert;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.guide.common.document.Guide;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 혈당 가이드 entity
 *
 * @author TEO
 * @since 1.0.0
 */
@Document
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BloodSugarGuide implements Guide {
	@Id
	private String id;
	private String oauthId;
	private LocalDate createdAt;
	private CommonCode type;
	private Alert alert;
	private String content;
	private String summary;

	@Builder
	private BloodSugarGuide(String id, String oauthId, LocalDate createdAt, CommonCode type, Alert alert, String content, String summary) {
		this.id = id;
		this.oauthId = oauthId;
		this.createdAt = createdAt;
		this.type = type;
		this.alert = alert;
		this.content = content;
		this.summary = summary;
	}

	public void update(BloodSugarAnalysisData data, String content, String summary) {
		this.type = data.getType();
		this.alert = data.getAlert();
		this.content = content;
		this.summary = summary;
	}
}
