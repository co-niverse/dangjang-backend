package com.coniverse.dangjang.domain.healthmetric.controller;

import static com.coniverse.dangjang.fixture.HealthMetricChartFixture.*;
import static com.coniverse.dangjang.support.SimpleMockMvc.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.coniverse.dangjang.domain.healthmetric.dto.response.BloodSugarMinMax;
import com.coniverse.dangjang.domain.healthmetric.dto.response.HealthMetricChartData;
import com.coniverse.dangjang.domain.healthmetric.service.HealthMetricChartSearchService;
import com.coniverse.dangjang.support.ControllerTest;
import com.coniverse.dangjang.support.annotation.WithDangjangUser;

/**
 * @author EVE
 * @since 1.0.0
 */
@WithDangjangUser
class HealthMetricChartControllerTest extends ControllerTest {
	public static final String URL = "/api/health-metric/chart";
	@Autowired
	private HealthMetricChartSearchService healthMetricChartSearchService;
	public static LocalDate 시작_날짜 = LocalDate.parse("2021-01-01");
	public static LocalDate 마지막_날짜 = LocalDate.parse("2021-01-07");
	public static LocalDate 생성_날짜 = LocalDate.of(2021, 1, 1);
	public static List<BloodSugarMinMax> 혈당차트 = 혈당차트_생성(생성_날짜, 100, 200);
	public static List<HealthMetricChartData> 체중차트 = 체중차트_생성(생성_날짜, 100);
	public static List<HealthMetricChartData> 걸음수차트 = 걸음수차트_생성(생성_날짜, 10000);
	public static List<HealthMetricChartData> 칼로리차트 = 칼로리차트_생성(생성_날짜, 400);

	@Test
	void 건강지표_차트_데이터를_조회하면_성공을_반한환다() throws Exception {
		//given
		doReturn(건강지표_차트_응답_생성(시작_날짜, 마지막_날짜, 혈당차트, 체중차트, 걸음수차트, 칼로리차트)).when(healthMetricChartSearchService).findHealthMetricChart(any(), any(), any());
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("startDate", 시작_날짜.toString());
		params.add("endDate", 마지막_날짜.toString());

		//when
		ResultActions resultActions = get(mockMvc, URL, params);

		//then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value("OK"),
			jsonPath("$.data.bloodSugars").exists(),
			jsonPath("$.data.weights").exists(),
			jsonPath("$.data.stepCounts").exists(),
			jsonPath("$.data.exerciseCalories").exists()
		);
	}

	@Test
	void 유효하지_않는_날짜로_차트를_조회하면_400에러를_반환한다() throws Exception {
		//given
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("startDate", "2021-01-51");
		params.add("endDate", 마지막_날짜.toString());

		//when
		ResultActions resultActions = get(mockMvc, URL, params);

		//then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.errorCode").value(400)
		);
	}
}
