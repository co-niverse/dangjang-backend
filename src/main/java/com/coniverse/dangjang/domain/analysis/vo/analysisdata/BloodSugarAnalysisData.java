package com.coniverse.dangjang.domain.analysis.vo.analysisdata;

import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.user.entity.User;

import lombok.Getter;

/**
 * 혈당 분석 데이터 vo
 *
 * @author TEO
 * @since 1.0.0
 */
@Getter
public class BloodSugarAnalysisData extends HealthMetricAnalysisData {
	private int unit;
	private final boolean diabetic;
	private final boolean medicine;
	private final boolean injection;

	public BloodSugarAnalysisData(HealthMetric healthMetric, User user) {
		super(healthMetric.getCreatedAt(), healthMetric.getType(), user.getOauthId());
		convertUnit(healthMetric.getUnit());
		this.diabetic = true; // TODO 유저 정보 mapping
		this.medicine = true;
		this.injection = true;
	}

	@Override
	void convertUnit(String unit) {
		this.unit = Integer.parseInt(unit);
	}
}
