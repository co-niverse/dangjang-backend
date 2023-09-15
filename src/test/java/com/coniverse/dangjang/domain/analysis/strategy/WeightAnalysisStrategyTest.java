package com.coniverse.dangjang.domain.analysis.strategy;

import static com.coniverse.dangjang.fixture.AnalysisDataFixture.*;
import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coniverse.dangjang.domain.analysis.dto.healthMetric.WeightAnalysisData;
import com.coniverse.dangjang.domain.analysis.enums.Alert;

/**
 * @author EVE
 * @since 1.0.0
 */
@SpringBootTest
class WeightAnalysisStrategyTest {
	@Autowired
	private WeightAnalysisStrategy weightAnalysisStrategy;

	private static Stream<Arguments> provideWeightAnalysisData() {
		return Stream.of(
			Arguments.of("40", Alert.LOW_WEIGHT),
			Arguments.of("90", Alert.NORMAL_WEIGHT),
			Arguments.of("95", Alert.OVERWEIGHT),
			Arguments.of("100", Alert.LEVEL_1_OBESITY),
			Arguments.of("120", Alert.LEVEL_2_OBESITY),
			Arguments.of("180", Alert.LEVEL_3_OBESITY)
		);
	}

	@ParameterizedTest
	@MethodSource("provideWeightAnalysisData")
	void 체중_수치에_따라_알맞은_경보를_반환한다(String unit, Alert alert) {
		// given & when
		WeightAnalysisData weightAnalysisData = (WeightAnalysisData)weightAnalysisStrategy.analyze(체중_분석_데이터(unit));

		//then
		assertThat(weightAnalysisData.getUnit()).isEqualTo(Integer.parseInt(unit));
		assertThat(weightAnalysisData.getAlert()).isEqualTo(alert);
	}
}
