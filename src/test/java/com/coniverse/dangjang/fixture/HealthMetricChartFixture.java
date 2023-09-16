package com.coniverse.dangjang.fixture;

import java.time.LocalDate;
import java.util.List;

import com.coniverse.dangjang.domain.healthmetric.dto.response.BloodSugarMinMax;
import com.coniverse.dangjang.domain.healthmetric.dto.response.HealthMetricChartData;
import com.coniverse.dangjang.domain.healthmetric.dto.response.HealthMetricChartResponse;

/**
 * 건강지표 차트 fixture
 *
 * @author EVE
 * @since 1.0.0
 */
public class HealthMetricChartFixture {
	public static List<BloodSugarMinMax> 혈당차트_생성(LocalDate 생성_일자, int 최소_혈당, int 최대_혈당) {
		return List.of(new BloodSugarMinMax(생성_일자, 최소_혈당, 최대_혈당));
	}

	public static List<HealthMetricChartData> 체중차트_생성(LocalDate 생성_일자, int 체중) {
		return List.of(new HealthMetricChartData(생성_일자, 체중));
	}

	public static List<HealthMetricChartData> 걸음수차트_생성(LocalDate 생성_일자, int 걸음수) {
		return List.of(new HealthMetricChartData(생성_일자, 걸음수));
	}

	public static List<HealthMetricChartData> 칼로리차트_생성(LocalDate 생성_일자, int 칼로리) {
		return List.of(new HealthMetricChartData(생성_일자, 칼로리));
	}

	public static HealthMetricChartResponse 건강지표_차트_응답_생성(LocalDate 시작_날짜, LocalDate 마지막_날짜, List<BloodSugarMinMax> 혈당차트, List<HealthMetricChartData> 체중차트,
		List<HealthMetricChartData> 걸음수차트, List<HealthMetricChartData> 운동칼로리차트) {
		return new HealthMetricChartResponse(시작_날짜, 마지막_날짜, 혈당차트, 체중차트, 걸음수차트, 운동칼로리차트);
	}
}
