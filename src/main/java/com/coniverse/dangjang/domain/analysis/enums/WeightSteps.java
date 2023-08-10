package com.coniverse.dangjang.domain.analysis.enums;

public enum WeightSteps {
	underweight("저체중"),
	normal("정상체중"),
	overweight("과체중"),
	obesity("경도비만"),
	mildObesity("중등도비만"),
	highObesity("고도비만");

	private final String title;

	WeightSteps(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

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

