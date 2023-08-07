package com.coniverse.dangjang.domain.analysis.enums;

public enum WeightSteps {
	underweight,
	normal,
	overweight,
	obesity,
	mildObesity,
	highObesity;

	public static WeightSteps of(double bmi) {
		if (bmi < 18.5) {
			return underweight;
		} else if (bmi < 22.9) {
			return normal;
		} else if (bmi < 24.9) {
			return overweight;
		} else if (bmi < 29.9) {
			return obesity;
		} else if (bmi < 34.9) {
			return mildObesity;
		} else {
			return highObesity;
		}
	}
}

