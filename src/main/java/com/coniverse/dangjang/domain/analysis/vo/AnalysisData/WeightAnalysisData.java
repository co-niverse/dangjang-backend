package com.coniverse.dangjang.domain.analysis.vo.AnalysisData;

import com.coniverse.dangjang.domain.analysis.enums.WeightSteps;
import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.entity.enums.Gender;

import lombok.Getter;

@Getter
public class WeightAnalysisData extends AbstractAnalysisData {
	private int unit;
	private int weightDiff;
	private WeightSteps weightSteps;

	public WeightAnalysisData(HealthMetric healthMetric, User user) {
		super(healthMetric.getCreatedAt(), healthMetric.getCommonCode(), user.getOauthId());
		double bmi = (this.unit / Math.pow(user.getHeight() / 100.0, 2.0));
		this.weightSteps = WeightSteps.of(bmi);
		this.weightDiff = getWeightDiff(user.getGender(), user.getHeight());
	}

	@Override
	void convertUnit(String unit) {
		this.unit = Integer.parseInt(unit);
	}

	int getWeightDiff(Gender gender, int height) {
		int standardWeight;
		if (gender.equals(Gender.M)) {
			standardWeight = (int)(Math.pow(height / 100.0, 2.0) * 22);
		} else {
			standardWeight = (int)(Math.pow(height / 100.0, 2.0) * 21);
		}
		return this.unit - standardWeight;
	}

}
