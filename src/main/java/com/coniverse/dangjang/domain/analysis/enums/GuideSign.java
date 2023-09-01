package com.coniverse.dangjang.domain.analysis.enums;

public enum GuideSign {
	positive,
	negative;

	public static GuideSign of(int value) {
		if (value < 0) {
			return negative;
		} else {
			return positive;
		}
	}
}
