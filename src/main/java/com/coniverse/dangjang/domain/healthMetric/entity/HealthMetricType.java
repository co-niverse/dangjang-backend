package com.coniverse.dangjang.domain.healthMetric.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HealthMetricType {
	BED_TIME("취침시간"),
	WAKE_UP_TIME("기상시간"),
	EMPTY_STOMACH("공복"),
	BEFORE_BREAKFAST("아침식전"),
	AFTER_BREAKFAST("아침식후"),
	BEFORE_LUNCH("점심식전"),
	AFTER_LUNCH("점심식후"),
	BEFORE_DINNER("저녁식전"),
	AFTER_DINNER("저녁식후"),
	BEFORE_SLEEP("취침전"),
	SYSTOLIC("수축"),
	DIASTOLIC("이완"),
	MEASUREMENT("몸무게"),
	HBA1C("당화혈색소"),
	HIGH_INTENSITY_MINUTES("고강도운동"),
	MODERATE_INTENSITY_MINUTES("중강도운동"),
	STEP_COUNT("걸음수");

	private final String title;
}
