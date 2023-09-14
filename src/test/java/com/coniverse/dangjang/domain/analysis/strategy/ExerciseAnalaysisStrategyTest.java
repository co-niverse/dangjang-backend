package com.coniverse.dangjang.domain.analysis.strategy;

import static com.coniverse.dangjang.fixture.AnalysisDataFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.coniverse.dangjang.domain.analysis.dto.healthMetric.ExerciseAnalysisData;
import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthMetricPostRequest;
import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.healthmetric.mapper.HealthMetricMapper;
import com.coniverse.dangjang.domain.healthmetric.service.HealthMetricSearchService;
import com.coniverse.dangjang.domain.user.entity.User;

@SpringBootTest
class ExerciseAnalaysisStrategyTest {
	@Autowired
	private ExerciseAnalysisStrategy exerciseAnalysisStrategy;
	@Autowired
	private HealthMetricMapper mapper;
	@MockBean
	private HealthMetricSearchService healthMetricSearchService;

	@ParameterizedTest
	@MethodSource("com.coniverse.dangjang.fixture.AnalysisDataFixture#운동분석_입력_파라미터")
	void 운동_수치에_맞게_분석한다(HealthMetricPostRequest reqeust, User user, int needByTTS, int needByLastWeek, int calorie) {
		//given
		HealthMetric weightHelthMetric = mapper.toEntity(체중_요청("70"), user);
		doReturn(weightHelthMetric).when(healthMetricSearchService).findLastHealthMetricById(any(), any());

		HealthMetric healthMetric = mapper.toEntity(reqeust, user);
		ExerciseAnalysisData exerciseAnalysisData = (ExerciseAnalysisData)exerciseAnalysisStrategy.createAnalysisData(healthMetric);

		//when
		ExerciseAnalysisData 운동_분석_데이터 = (ExerciseAnalysisData)exerciseAnalysisStrategy.analyze(exerciseAnalysisData);
		//then
		assertThat(운동_분석_데이터.getUnit()).isEqualTo(Integer.parseInt(reqeust.unit()));
		assertThat(운동_분석_데이터.getType().getTitle()).isEqualTo(reqeust.type());
		assertThat(운동_분석_데이터.needStepByTTS).isEqualTo(needByTTS);
		assertThat(운동_분석_데이터.needStepByLastWeek).isEqualTo(needByLastWeek);
		assertThat(운동_분석_데이터.calorie).isEqualTo(calorie);

	}
}
