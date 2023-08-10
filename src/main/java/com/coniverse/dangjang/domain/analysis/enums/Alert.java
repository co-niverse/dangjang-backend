package com.coniverse.dangjang.domain.analysis.enums;

import com.coniverse.dangjang.global.support.enums.EnumCode;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Alert implements EnumCode {
	HYPOGLYCEMIA("저혈당"),
	NORMAL("정상"),
	CAUTION("주의"),
	WARNING("경고"),
	PRE_HYPERTENSION("고혈압 전단계"),
	HYPERTENSION("고혈압"),
	LOW_WEIGHT("저체중"),
	NORMAL_WEIGHT("정상체중"),
	OVERWEIGHT("과체중"),
	LEVEL_1_OBESITY("경도 비만"),
	LEVEL_2_OBESITY("중등도 비만"),
	LEVEL_3_OBESITY("고도 비만"),
	REGULAR_SLEEP("규칙적인 수면"),
	IRREGULAR_SLEEP("불규칙적인 수면"),
	;

	private final String title;

	@Override
	public String getCode() {
		return name();
	}

	@Override
	public String getTitle() {
		return this.title;
	}
}
