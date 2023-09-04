package com.coniverse.dangjang.domain.analysis.enums;

/**
 * WeightSteps 체중 단계 ENUM
 *
 * @author EVE
 * @since 1.0.0
 */
public enum WeightSteps {
	UNDERWEIGHT("저체중"),
	NORMAL("정상체중"),
	OVERWEIGHT("과체중"),
	OBESITY("경도비만"),
	MILDOBESITY("중등도비만"),
	HIGHOBESITY("고도비만");

	private final String title;

	WeightSteps(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

	public static WeightSteps of(double bmi) {
		if (bmi < 18.5) {
			return UNDERWEIGHT;
		} else if (bmi < 22.9) {
			return NORMAL;
		} else if (bmi < 24.9) {
			return OVERWEIGHT;
		} else if (bmi < 29.9) {
			return OBESITY;
		} else if (bmi < 34.9) {
			return MILDOBESITY;
		} else {
			return HIGHOBESITY;
		}
	}
}

