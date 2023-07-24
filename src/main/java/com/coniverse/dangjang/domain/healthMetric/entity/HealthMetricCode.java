package com.coniverse.dangjang.domain.healthMetric.entity;

import java.util.Arrays;
import java.util.List;

import com.coniverse.dangjang.domain.healthMetric.exception.HealthMetricCodeNotFoundException;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 건강지표 코드
 *
 * @author TEO
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum HealthMetricCode {
	SLEEP("수면", List.of(HealthMetricType.BED_TIME, HealthMetricType.WAKE_UP_TIME)),
	BLOOD_PRESSURE("혈압", List.of(HealthMetricType.SYSTOLIC, HealthMetricType.DIASTOLIC)),
	BLOOD_SUGAR("혈당", List.of(
		HealthMetricType.EMPTY_STOMACH, HealthMetricType.BEFORE_BREAKFAST, HealthMetricType.AFTER_BREAKFAST, HealthMetricType.BEFORE_LUNCH,
		HealthMetricType.AFTER_LUNCH, HealthMetricType.BEFORE_DINNER, HealthMetricType.AFTER_DINNER, HealthMetricType.BEFORE_SLEEP, HealthMetricType.ETC)
	),
	WEIGHT("체중", List.of(HealthMetricType.MEASUREMENT)),
	EXERCISE("운동", List.of(HealthMetricType.HIGH_INTENSITY_MINUTES, HealthMetricType.MODERATE_INTENSITY_MINUTES, HealthMetricType.STEP_COUNT)),
	GLYCATED_HB("당화혈색소", List.of(HealthMetricType.HBA1C));

	private final String title;
	private final List<HealthMetricType> typeList;

	/**
	 * 건강지표 타입으로 건강지표 코드를 찾는다.
	 *
	 * @param type 건강지표 타입
	 * @return HealthMetricCode 건강지표 코드
	 * @throws HealthMetricCodeNotFoundException 건강지표 코드를 찾을 수 없을 때 발생하는 예외
	 * @see HealthMetricType
	 * @since 1.0.0
	 */
	public static HealthMetricCode findByHealthMetricsType(HealthMetricType type) {
		return Arrays.stream(HealthMetricCode.values())
			.filter(code -> code.hasType(type))
			.findAny()
			.orElseThrow(HealthMetricCodeNotFoundException::new);
	}

	/**
	 * 건강지표 타입이 건강지표 코드에 포함되어 있는지 확인한다.
	 *
	 * @param type 건강지표 타입
	 * @return boolean 건강지표 타입 포함 여부
	 * @see HealthMetricType
	 * @since 1.0.0
	 */
	private boolean hasType(HealthMetricType type) {
		return typeList.stream().anyMatch(t -> t.equals(type));
	}
}
