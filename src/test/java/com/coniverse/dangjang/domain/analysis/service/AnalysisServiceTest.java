package com.coniverse.dangjang.domain.analysis.service;

import static com.coniverse.dangjang.fixture.CommonCodeFixture.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import com.coniverse.dangjang.domain.analysis.strategy.AnalysisStrategy;
import com.coniverse.dangjang.domain.analysis.strategy.BloodSugarAnalysisStrategy;
import com.coniverse.dangjang.domain.analysis.strategy.WeightAnalysisStrategy;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.fixture.HealthMetricFixture;

/**
 * @author TEO
 * @since 1.0.0
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AnalysisServiceTest {
	@Autowired
	private AnalysisService analysisService;
	@SpyBean
	private BloodSugarAnalysisStrategy bloodSugarAnalysisStrategy;
	@SpyBean
	private WeightAnalysisStrategy weightAnalysisStrategy;

	private Stream<Arguments> provideHealthMetricType() {
		return Stream.of(
			Arguments.of(혈당_타입(), bloodSugarAnalysisStrategy),
			Arguments.of(체중_타입(), weightAnalysisStrategy)
			// TODO 운동, 당화혈색소, 식단 추가
		);
	}

	@ParameterizedTest
	@MethodSource("provideHealthMetricType")
	void 건강지표_타입에_따라_알맞은_분석_전략을_선택하여_호출한다(List<CommonCode> type, AnalysisStrategy analysisStrategy) {
		// given & when
		type.stream()
			.map(HealthMetricFixture::건강지표_엔티티)
			.map(analysisService::analyze)
			.forEach(analysisData -> verify(analysisStrategy).analyze(analysisData));
	}
}