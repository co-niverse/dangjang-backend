package com.coniverse.dangjang.domain.analysis.enums;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.coniverse.dangjang.domain.analysis.exception.NonAnalyticDataException;

/**
 * @author EVE
 * @since 1.0.0
 */
class BmiAlertTest {
	private static Stream<Arguments> provideBmi() {
		return Stream.of(
			Arguments.of(14.5, Alert.LOW_WEIGHT),
			Arguments.of(20.9, Alert.NORMAL_WEIGHT),
			Arguments.of(23.7, Alert.OVERWEIGHT),
			Arguments.of(28.6, Alert.LEVEL_1_OBESITY),
			Arguments.of(32.6, Alert.LEVEL_2_OBESITY),
			Arguments.of(35.3, Alert.LEVEL_3_OBESITY)
		);
	}

	@ParameterizedTest
	@MethodSource("provideBmi")
	void bmi_수치에_따라_경보를_반환한다(double bmi, Alert alert) {
		// when & then
		assertThat(BmiAlert.findAlertByBmi(bmi)).isEqualTo(alert);
	}

	@ParameterizedTest
	@ValueSource(doubles = {-1.0, -493.45})
	void 잘못된_bmi_수치는_예외를_발생한다(double bmi) {
		// when & then
		assertThatThrownBy(() -> BmiAlert.findAlertByBmi(bmi))
			.isInstanceOf(NonAnalyticDataException.class);
	}
}
