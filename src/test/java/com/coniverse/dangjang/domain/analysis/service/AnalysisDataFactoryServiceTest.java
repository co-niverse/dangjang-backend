package com.coniverse.dangjang.domain.analysis.service;

import static com.coniverse.dangjang.fixture.HealthMetricFixture.*;
import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coniverse.dangjang.domain.analysis.dto.healthMetric.BloodSugarAnalysisData;
import com.coniverse.dangjang.domain.analysis.dto.healthMetric.HealthMetricAnalysisData;
import com.coniverse.dangjang.domain.code.enums.GroupCode;

/**
 * @author TEO
 * @since 1.0.0
 */
@SpringBootTest
public class AnalysisDataFactoryServiceTest {
	@Autowired
	private AnalysisDataFactoryService analysisDataFactoryService;

	private static Stream<Arguments> provideHealthMetric() {
		return Stream.of(
			Arguments.of(GroupCode.BLOOD_SUGAR, BloodSugarAnalysisData.class)
			// TODO 체중, 운동, 당화혈색소, 식단 추가
		);
	}

	@ParameterizedTest
	@MethodSource("provideHealthMetric")
	void 건강지표_타입에_맞는_분석_데이터를_생성한다(GroupCode groupCode, Class<? extends HealthMetricAnalysisData> expected) {
		// given
		groupCode.getCommonCodeList().stream()
			// when
			.map(t -> 건강지표_엔티티(t))
			.map(analysisDataFactoryService::createHealthMetricAnalysisData)
			// then
			.forEach(d -> assertThat(d).isInstanceOf(expected));
	}
}
