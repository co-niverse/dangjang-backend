package com.coniverse.dangjang.domain.healthmetric.enums;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author TEO
 * @since 1.0.0
 */
public class HealthMetricEnumTest {
	private static Stream<Arguments> provideHealthMetricType() {
		return Stream.of(
			Arguments.of("취침시간", HealthMetricType.BED_TIME),
			Arguments.of("공복", HealthMetricType.EMPTY_STOMACH),
			Arguments.of("아침식전", HealthMetricType.BEFORE_BREAKFAST),
			Arguments.of("점심식전", HealthMetricType.BEFORE_LUNCH),
			Arguments.of("저녁식후", HealthMetricType.AFTER_DINNER),
			Arguments.of("취침전", HealthMetricType.BEFORE_SLEEP),
			Arguments.of("수축", HealthMetricType.SYSTOLIC),
			Arguments.of("당화혈색소", HealthMetricType.HBA1C),
			Arguments.of("고강도운동", HealthMetricType.HIGH_INTENSITY_MINUTES)
		);
	}

	private static Stream<Arguments> provideHealthMetricCode() {
		return Stream.of(
			Arguments.of(HealthMetricCode.SLEEP, HealthMetricType.BED_TIME),
			Arguments.of(HealthMetricCode.BLOOD_SUGAR, HealthMetricType.EMPTY_STOMACH),
			Arguments.of(HealthMetricCode.BLOOD_SUGAR, HealthMetricType.BEFORE_BREAKFAST),
			Arguments.of(HealthMetricCode.BLOOD_SUGAR, HealthMetricType.BEFORE_LUNCH),
			Arguments.of(HealthMetricCode.BLOOD_SUGAR, HealthMetricType.AFTER_DINNER),
			Arguments.of(HealthMetricCode.BLOOD_SUGAR, HealthMetricType.BEFORE_SLEEP),
			Arguments.of(HealthMetricCode.BLOOD_PRESSURE, HealthMetricType.SYSTOLIC),
			Arguments.of(HealthMetricCode.GLYCATED_HB, HealthMetricType.HBA1C),
			Arguments.of(HealthMetricCode.EXERCISE, HealthMetricType.HIGH_INTENSITY_MINUTES),
			Arguments.of(HealthMetricCode.WEIGHT, HealthMetricType.MEASUREMENT)
		);
	}

	@ParameterizedTest
	@MethodSource("provideHealthMetricType")
	void 제목으로_건강지표_타입을_찾는다(String title, HealthMetricType type) {
		assertThat(HealthMetricType.findByTitle(title)).isEqualTo(type);
	}

	@ParameterizedTest
	@MethodSource("provideHealthMetricCode")
	void 건강지표_타입으로_건강지표_코드를_찾는다(HealthMetricCode code, HealthMetricType type) {
		assertThat(HealthMetricCode.findByHealthMetricType(type)).isEqualTo(code);
	}
}
