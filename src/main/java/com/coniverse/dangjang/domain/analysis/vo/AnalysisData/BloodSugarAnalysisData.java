package com.coniverse.dangjang.domain.analysis.vo.AnalysisData;

import java.util.List;

import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.user.entity.User;

import lombok.Getter;

@Getter
public class BloodSugarAnalysisData extends AbstractAnalysisData {
	private int unit;
	private final boolean diabetes;
	private final boolean medicine;
	private final boolean injection;
	private final List<String> diseases;

	public BloodSugarAnalysisData(HealthMetric healthMetric, User user) {
		super(healthMetric.getCreatedAt(), healthMetric.getCommonCode(), user.getOauthId());
		convertUnit(healthMetric.getUnit());
		this.diabetes = true; // TODO 유저 정보 mapping
		this.medicine = true;
		this.injection = true;
		this.diseases = List.of("저혈압");
	}

	@Override
	void convertUnit(String unit) {
		this.unit = Integer.parseInt(unit);
	}
}
