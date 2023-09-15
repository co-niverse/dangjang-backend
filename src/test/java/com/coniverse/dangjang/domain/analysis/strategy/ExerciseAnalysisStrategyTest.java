package com.coniverse.dangjang.domain.analysis.strategy;

import static com.coniverse.dangjang.fixture.HealthMetricFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.coniverse.dangjang.domain.analysis.dto.healthMetric.ExerciseAnalysisData;
import com.coniverse.dangjang.domain.analysis.enums.ExerciseCoefficient;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.healthmetric.service.HealthMetricSearchService;

/**
 * @author EVE
 * @since 1.0.0
 */
@SpringBootTest
class ExerciseAnalysisStrategyTest {
	@Autowired
	private ExerciseAnalysisStrategy exerciseAnalysisStrategy;
	@MockBean
	private HealthMetricSearchService healthMetricSearchService;

	private static Stream<Arguments> 운동분석_입력_파라미터() {
		return Stream.of(
			Arguments.of(건강지표_엔티티(CommonCode.STEP_COUNT, "10000"), 0, 10000, 0),
			Arguments.of(건강지표_엔티티(CommonCode.RUN, "100"), 0, 0, calculateCalorie(CommonCode.RUN, 100, 70)),
			Arguments.of(건강지표_엔티티(CommonCode.BIKE, "10"), 0, 0, calculateCalorie(CommonCode.BIKE, 10, 70)),
			Arguments.of(건강지표_엔티티(CommonCode.HIKING, "60"), 0, 0, calculateCalorie(CommonCode.HIKING, 60, 70)),
			Arguments.of(건강지표_엔티티(CommonCode.WALK, "120"), 0, 0, calculateCalorie(CommonCode.WALK, 120, 70)),
			Arguments.of(건강지표_엔티티(CommonCode.SWIM, "120"), 0, 0, calculateCalorie(CommonCode.SWIM, 120, 70))
		);
	}

	private static int calculateCalorie(CommonCode type, int unit, int weight) {
		double percent = ExerciseCoefficient.findCoefficientByType(type);
		return (int)(percent * weight / 15 * unit);
	}

	@ParameterizedTest
	@MethodSource("운동분석_입력_파라미터")
	void 운동_수치에_맞게_분석한다(HealthMetric healthMetric, int needByTTS, int needByLastWeek, int calorie) {
		// given
		doReturn(건강지표_엔티티(CommonCode.MEASUREMENT, "70"))
			.when(healthMetricSearchService).findLastHealthMetricById(any(), any());

		ExerciseAnalysisData exerciseAnalysisData = (ExerciseAnalysisData)exerciseAnalysisStrategy.createAnalysisData(healthMetric);

		// when
		ExerciseAnalysisData 운동_분석_데이터 = (ExerciseAnalysisData)exerciseAnalysisStrategy.analyze(exerciseAnalysisData);

		// then
		assertThat(운동_분석_데이터.getUnit()).isEqualTo(Integer.parseInt(healthMetric.getUnit()));
		assertThat(운동_분석_데이터.getType()).isEqualTo(healthMetric.getType());
		assertThat(운동_분석_데이터.needStepByTTS).isEqualTo(needByTTS);
		assertThat(운동_분석_데이터.needStepByLastWeek).isEqualTo(needByLastWeek);
		assertThat(운동_분석_데이터.calorie).isEqualTo(calorie);

	}
}
