package com.coniverse.dangjang.domain.analysis.dto.healthMetric;

import com.coniverse.dangjang.domain.analysis.enums.Alert;
import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.user.entity.User;

import lombok.Getter;

/**
 * 혈당 분석 데이터
 *
 * @author TEO
 * @since 1.0.0
 */
@Getter
public class BloodSugarAnalysisData extends HealthMetricAnalysisData {
	private final boolean diabetic;
	private final boolean medicine;
	private final boolean injection;
	private int unit;
	private Alert alert;
	private int deviation;
	private boolean lackOfExercise = false;
	private boolean overweight = false;

	public BloodSugarAnalysisData(HealthMetric healthMetric) {
		super(healthMetric);
		convertUnit(healthMetric.getUnit());
		User user = healthMetric.getUser();
		this.diabetic = user.isDiabetic();
		this.medicine = user.isMedicine();
		this.injection = user.isInjection();
	}

	@Override
	void convertUnit(String unit) {
		this.unit = Integer.parseInt(unit);
	}

	public void setAlert(Alert alert) {
		this.alert = alert;
	}

	public void setDeviation(int deviation) {
		this.deviation = deviation;
	}

	public void setLackOfExercise(boolean lackOfExercise) {
		this.lackOfExercise = lackOfExercise;
	}

	public void setOverweight(boolean overweight) {
		this.overweight = overweight;
	}
}
