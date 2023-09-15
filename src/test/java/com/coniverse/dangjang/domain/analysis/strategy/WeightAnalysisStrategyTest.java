package com.coniverse.dangjang.domain.analysis.strategy;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coniverse.dangjang.domain.analysis.dto.healthMetric.WeightAnalysisData;
import com.coniverse.dangjang.domain.analysis.enums.Alert;
import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.healthmetric.mapper.HealthMetricMapper;

/**
 * @author EVE
 * @since 1.0.0
 */
@SpringBootTest
class WeightAnalysisStrategyTest {
	@Autowired
	private WeightAnalysisStrategy weightAnalysisStrategy;
	@Autowired
	private HealthMetricMapper mapper;

	@ParameterizedTest
	@MethodSource("com.coniverse.dangjang.fixture.AnalysisDataFixture#체중분석_입력_파라미터")
	void 체중_수치에_맞게_분석한다(HealthMetric weightHealthMetric, Alert 경보) {
		//given

		WeightAnalysisData AnalysisData = (WeightAnalysisData)weightAnalysisStrategy.createAnalysisData(weightHealthMetric);

		//when
		WeightAnalysisData weightAnalysisData = (WeightAnalysisData)weightAnalysisStrategy.analyze(AnalysisData);
		//then

		assertThat(weightAnalysisData.getUnit()).isEqualTo(Integer.parseInt(weightHealthMetric.getUnit()));
		assertThat(weightAnalysisData.getType()).isEqualTo(weightHealthMetric.getType());
		assertThat(weightAnalysisData.getAlert()).isEqualTo(경보);

	}

}
