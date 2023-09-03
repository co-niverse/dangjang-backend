package com.coniverse.dangjang.domain.analysis.service;

import static com.coniverse.dangjang.fixture.AnalysisDataFixture.*;
import static org.mockito.Mockito.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import com.coniverse.dangjang.domain.analysis.dto.AnalysisData;
import com.coniverse.dangjang.domain.analysis.strategy.AnalysisStrategy;
import com.coniverse.dangjang.domain.analysis.strategy.BloodSugarAnalysisStrategy;

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

	private Stream<Arguments> provideAnalysisData() {
		return Stream.of(
			Arguments.of(혈당_분석_데이터(), bloodSugarAnalysisStrategy)
			// TODO 운동, 체중, 당화혈색소, 식단 추가
		);
	}

	@ParameterizedTest
	@MethodSource("provideAnalysisData")
	void 데이터에_따라_알맞은_분석_전략을_선택하여_호출한다(AnalysisData 분석_데이터, AnalysisStrategy analysisStrategy) {
		// given & when
		analysisService.analyze(분석_데이터);

		// then
		verify(analysisStrategy, atLeastOnce()).analyze(분석_데이터);
	}
}