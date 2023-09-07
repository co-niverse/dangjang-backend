package com.coniverse.dangjang.domain.analysis.dto.healthMetric;

import java.time.LocalDate;

import com.coniverse.dangjang.domain.analysis.dto.AnalysisData;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;

import lombok.Getter;

/**
 * 건강 지표 분석 데이터 추상 클래스
 *
 * @author TEO
 * @since 1.0.0
 */
@Getter
public abstract class HealthMetricAnalysisData implements AnalysisData {
	private final String oauthId;
	private final LocalDate createdAt;
	private final CommonCode type;

	protected HealthMetricAnalysisData(HealthMetric healthMetric) {
		this.oauthId = healthMetric.getUser().getOauthId();
		this.createdAt = healthMetric.getCreatedAt();
		this.type = healthMetric.getType();
	}

	/**
	 * 각 분석 데이터마다 단위의 타입이 다르기 때문에 타입을 변환한다
	 *
	 * @param unit 변환할 단위
	 * @since 1.0.0
	 */
	abstract void convertUnit(String unit);
}
