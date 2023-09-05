package com.coniverse.dangjang.domain.analysis.dto.healthMetric;

import com.coniverse.dangjang.domain.analysis.enums.Alert;
import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.entity.enums.Gender;

import lombok.Getter;

/**
 * 체중분석데이터
 *
 * @author EVE
 * @since 1.0.0
 */
@Getter
public class WeightAnalysisData extends HealthMetricAnalysisData {
	private final int height;
	private final Gender gender;
	private int unit;
	private int weightDiff;
	private Alert alert;

	public WeightAnalysisData(HealthMetric healthMetric) {
		super(healthMetric);
		convertUnit(healthMetric.getUnit());
		User user = healthMetric.getUser();
		this.height = user.getHeight();
		this.gender = user.getGender();
	}

	@Override
	void convertUnit(String unit) {
		this.unit = Integer.parseInt(unit);
	}

	public void setAlert(Alert alert) {
		this.alert = alert;
	}

	public void setWeightDiff(int weightDiff) {
		this.weightDiff = weightDiff;
	}
}
