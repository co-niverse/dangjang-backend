package com.coniverse.dangjang.domain.healthMetric.entity;

import java.util.Arrays;
import java.util.List;

import com.coniverse.dangjang.domain.healthMetric.exception.HealthMetricCodeNotFoundException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HealthMetricCode {
	SLEEP("수면", List.of(HealthMetricType.BED_TIME, HealthMetricType.WAKE_UP_TIME)),
	BLOOD_PRESSURE("혈압", List.of(HealthMetricType.SYSTOLIC, HealthMetricType.DIASTOLIC)),
	BLOOD_SUGAR("혈당", List.of(
		HealthMetricType.EMPTY_STOMACH, HealthMetricType.BEFORE_BREAKFAST, HealthMetricType.AFTER_BREAKFAST, HealthMetricType.BEFORE_LUNCH,
		HealthMetricType.AFTER_LUNCH, HealthMetricType.BEFORE_DINNER, HealthMetricType.AFTER_DINNER, HealthMetricType.BEFORE_SLEEP)
	),
	WEIGHT("체중", List.of(HealthMetricType.MEASUREMENT)),
	EXERCISE("운동", List.of(HealthMetricType.HIGH_INTENSITY_MINUTES, HealthMetricType.MODERATE_INTENSITY_MINUTES, HealthMetricType.STEP_COUNT)),
	GLYCATED_HB("당화혈색소", List.of(HealthMetricType.HBA1C));

	private final String title;
	private final List<HealthMetricType> typeList;

	public static HealthMetricCode findByHealthMetricsType(HealthMetricType type) {
		return Arrays.stream(HealthMetricCode.values())
			.filter(code -> code.hasType(type))
			.findAny()
			.orElseThrow(HealthMetricCodeNotFoundException::new);
	}

	private boolean hasType(HealthMetricType type) {
		return typeList.stream().anyMatch(t -> t.equals(type));
	}
}
