package com.coniverse.dangjang.domain.analysis.strategy;

import static com.coniverse.dangjang.fixture.HealthMetricFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.coniverse.dangjang.domain.analysis.dto.healthMetric.ExerciseAnalysisData;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.healthmetric.mapper.HealthMetricMapper;
import com.coniverse.dangjang.domain.healthmetric.service.HealthMetricSearchService;

/**
 * @author EVE
 * @since 1.0.0
 */
@SpringBootTest
class ExerciseAnalaysisStrategyTest {
	@Autowired
	private ExerciseAnalysisStrategy exerciseAnalysisStrategy;
	@Autowired
	private HealthMetricMapper mapper;
	@MockBean
	private HealthMetricSearchService healthMetricSearchService;

	private HealthMetric weightHealthMetric = 건강지표_엔티티(CommonCode.MEASUREMENT, "70");

	@ParameterizedTest
	@MethodSource("com.coniverse.dangjang.fixture.AnalysisDataFixture#운동분석_입력_파라미터")
	void 운동_수치에_맞게_분석한다(HealthMetric exerciseHealthMetric, int needByTTS, int needByLastWeek, int calorie) {
		//given

		doReturn(weightHealthMetric).when(healthMetricSearchService).findLastHealthMetricById(any(), any());

		ExerciseAnalysisData exerciseAnalysisData = (ExerciseAnalysisData)exerciseAnalysisStrategy.createAnalysisData(exerciseHealthMetric);

		//when
		ExerciseAnalysisData 운동_분석_데이터 = (ExerciseAnalysisData)exerciseAnalysisStrategy.analyze(exerciseAnalysisData);
		//then
		assertThat(운동_분석_데이터.getUnit()).isEqualTo(Integer.parseInt(exerciseHealthMetric.getUnit()));
		assertThat(운동_분석_데이터.getType().getTitle()).isEqualTo(exerciseHealthMetric.getType().getTitle());
		assertThat(운동_분석_데이터.needStepByTTS).isEqualTo(needByTTS);
		assertThat(운동_분석_데이터.needStepByLastWeek).isEqualTo(needByLastWeek);
		assertThat(운동_분석_데이터.calorie).isEqualTo(calorie);

	}
}
