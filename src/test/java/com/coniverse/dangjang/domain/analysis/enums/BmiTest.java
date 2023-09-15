package com.coniverse.dangjang.domain.analysis.enums;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

import com.coniverse.dangjang.domain.analysis.exception.NonAnalyticDataException;

@SpringBootTest
public class BmiTest {

	@ParameterizedTest
	@MethodSource("com.coniverse.dangjang.fixture.AnalysisDataFixture#bmi_입력_파라미터")
	void bmi_수치에_맞게_분석한다(double bmi, Alert alert) {
		//when&then
		assertThat(Bmi.calculateBmi(bmi)).isEqualTo(alert);

	}

	@ParameterizedTest
	@ValueSource(doubles = {-1.0, -493.45})
	void 잘못된_bmi_수치는_400에러를_반환한다(double bmi) {
		//given

		//when

		//then
		assertThrows(NonAnalyticDataException.class, () -> Bmi.calculateBmi(bmi));

	}
}
