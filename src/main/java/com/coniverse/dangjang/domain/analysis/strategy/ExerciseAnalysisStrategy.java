package com.coniverse.dangjang.domain.analysis.strategy;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import com.coniverse.dangjang.domain.analysis.dto.AnalysisData;
import com.coniverse.dangjang.domain.analysis.dto.healthMetric.ExerciseAnalysisData;
import com.coniverse.dangjang.domain.analysis.enums.ExerciseCoefficient;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.code.enums.GroupCode;
import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.healthmetric.service.HealthMetricSearchService;

import lombok.RequiredArgsConstructor;

/**
 * 운동 분석 전략
 *
 * @author EVE, TEO
 * @see ExerciseAnalysisData
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class ExerciseAnalysisStrategy implements AnalysisStrategy {
	private final HealthMetricSearchService healthMetricSearchService;

	/**
	 * 운동 분석 데이터를 생성한다.
	 *
	 * @param healthMetric 건강지표
	 * @return 운동 분석 데이터
	 * @since 1.0.0
	 */
	@Override
	public AnalysisData createAnalysisData(HealthMetric healthMetric) {
		return new ExerciseAnalysisData(healthMetric);
	}

	/**
	 * 운동 데이터를 분석한다.
	 *
	 * @param analysisData 운동 분석 데이터
	 * @return 운동 분석 데이터
	 * @since 1.0.0
	 */
	@Override
	public AnalysisData analyze(AnalysisData analysisData) {
		ExerciseAnalysisData data = (ExerciseAnalysisData)analysisData;
		if (data.getType().equals(CommonCode.STEP_COUNT)) {
			data.setNeedStepByTTS(this.calculateTTSDeviation(data.unit));
			data.setNeedStepByLastWeek(this.calculateLastWeekDeviation(data));
			return data;
		}
		data.setCalorie(this.calculateCalorie(data));
		return data;
	}

	@Override
	public GroupCode getGroupCode() {
		return GroupCode.EXERCISE;
	}

	/**
	 * 만보와 비교하여 편차를 계산한다.
	 *
	 * @param unit 걸음수
	 * @return 편차
	 * @since 1.0.0
	 */
	private int calculateTTSDeviation(int unit) {
		return unit - 10000;
	}

	/**
	 * 지난 주 평균 걸음수와 비교하여 편차를 계산한다.
	 *
	 * @param data 운동 분석 데이터
	 * @return 편차
	 * @since 1.0.0
	 */
	private int calculateLastWeekDeviation(ExerciseAnalysisData data) {
		LocalDate endDate = calculateLastWeekSunday(data.getCreatedAt());
		LocalDate startDate = endDate.minusDays(6);
		List<HealthMetric> lastWeekHealthMetricList = healthMetricSearchService.findWeeklyHealthMetricById(data.getOauthId(), data.getType(),
			startDate, endDate);

		return data.unit - (int)lastWeekHealthMetricList.stream()
			.mapToInt(metric -> Integer.parseInt(metric.getUnit()))
			.average()
			.orElse(0);
	}

	/**
	 * 지난주의 일요일 날짜를 계산한다.
	 *
	 * @param now 현재 날짜
	 * @return 지난주 일요일
	 * @since 1.0.0
	 */
	private LocalDate calculateLastWeekSunday(LocalDate now) {
		int numberOfDay = now.getDayOfWeek().getValue();
		return now.minusDays(numberOfDay);
	}

	/**
	 * 운동 소모 칼로리를 계산한다.	운동 계수는 {@link ExerciseCoefficient}에서 찾는다.
	 * <p>
	 * 분당 소모 칼로리 = (운동 계수 * 체중) / 15(분)
	 * <p>
	 * 총 소모 칼로리 = 분당 소모 칼로리 * 운동 시간(분)
	 *
	 * @param data 운동 분석 데이터
	 * @return 칼로리
	 * @since 1.0.0
	 */
	private int calculateCalorie(ExerciseAnalysisData data) {
		String weight = healthMetricSearchService.findLastHealthMetricById(data.getOauthId(), CommonCode.MEASUREMENT).getUnit();
		double coefficient = ExerciseCoefficient.findCoefficientByType(data.getType());
		return (int)(coefficient * Integer.parseInt(weight) / 15 * data.unit);
	}
}
