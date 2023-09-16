package com.coniverse.dangjang.domain.healthmetric.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.code.enums.GroupCode;
import com.coniverse.dangjang.domain.guide.exercise.document.ExerciseGuide;
import com.coniverse.dangjang.domain.guide.exercise.service.ExerciseGuideSearchService;
import com.coniverse.dangjang.domain.healthmetric.dto.response.BloodSugarMinMax;
import com.coniverse.dangjang.domain.healthmetric.dto.response.HealthMetricChartData;
import com.coniverse.dangjang.domain.healthmetric.dto.response.HealthMetricChartResponse;
import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.healthmetric.exception.HealthMetricNotFoundException;

import lombok.RequiredArgsConstructor;

/**
 * 건강 지표 차트 조회 service
 *
 * @author EVE
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class HealthMetricChartSearchService {
	private final HealthMetricSearchService healthMetricSearchService;
	private final ExerciseGuideSearchService exerciseGuideSearchService;

	/**
	 * 건강지표 차트 데이터를 조회한다.
	 *
	 * @param startDate 조회 시작 날짜
	 * @param endDate   조회 종료 날짜
	 * @param oauthId   유저 아이디
	 * @return HealthMetricChartResponse 건강지표 차트 데이터
	 * @author EVE
	 * @since 1.0.0
	 */

	public HealthMetricChartResponse findHealthMetricChart(String oauthId, LocalDate startDate, LocalDate endDate) {
		List<BloodSugarMinMax> bloodSugarMinMaxes = findBloodSugarMinMaxes(oauthId, startDate, endDate);
		List<HealthMetricChartData> weights = findWeights(oauthId, startDate, endDate);
		List<HealthMetricChartData> stepCounts = findStepCounts(oauthId, startDate, endDate);
		List<HealthMetricChartData> exerciseCalories = findExerciseCalories(oauthId, startDate, endDate);

		return new HealthMetricChartResponse(startDate, endDate, bloodSugarMinMaxes, weights, stepCounts, exerciseCalories);
	}

	/**
	 * 기한 내의 혈당 최저,최고 값을 조회한다.
	 *
	 * @param startDate 조회 시작 날짜
	 * @param endDate   조회 종료 날짜
	 * @param oauthId   유저 아이디
	 * @return List<BloodSugarMinMax> 혈당 최저,최고 값 리스트
	 * @since 1.0.0
	 */

	private List<BloodSugarMinMax> findBloodSugarMinMaxes(String oauthId, LocalDate startDate, LocalDate endDate) {
		List<BloodSugarMinMax> bloodSugarMinMaxes = new ArrayList<>();

		List<HealthMetric> bloodSugarHealthMetric = healthMetricSearchService.findWeeklyHealthMetricByGroupCode(oauthId, GroupCode.BLOOD_SUGAR,
			startDate, endDate);
		Map<LocalDate, List<HealthMetric>> bloodSugarMapByDate = bloodSugarHealthMetric.stream().collect(Collectors.groupingBy(HealthMetric::getCreatedAt));
		bloodSugarMapByDate.forEach((date, healthMetrics) -> {
			int minBloodSugar = healthMetrics.stream().mapToInt(healthMetric -> Integer.parseInt(healthMetric.getUnit())).min().orElseThrow(
				HealthMetricNotFoundException::new);
			int maxBloodSugar = healthMetrics.stream().mapToInt(healthMetric -> Integer.parseInt(healthMetric.getUnit())).max().orElseThrow(
				HealthMetricNotFoundException::new);
			bloodSugarMinMaxes.add(new BloodSugarMinMax(date, minBloodSugar, maxBloodSugar));

		});
		return bloodSugarMinMaxes;
	}

	/**
	 * 기한 내의 체중 값을 조회한다.
	 *
	 * @param startDate 조회 시작 날짜
	 * @param endDate   조회 종료 날짜
	 * @param oauthId   유저 아이디
	 * @return List<HealthMetricChartData> 체중 값 리스트
	 * @since 1.0.0
	 */
	private List<HealthMetricChartData> findWeights(String oauthId, LocalDate startDate, LocalDate endDate) {
		List<HealthMetricChartData> weights = new ArrayList<>();

		List<HealthMetric> healthMetrics = healthMetricSearchService.findWeeklyHealthMetricByGroupCode(oauthId, GroupCode.WEIGHT, startDate, endDate);
		healthMetrics.forEach(healthMetric ->
			weights.add(new HealthMetricChartData(healthMetric.getCreatedAt(), Integer.parseInt(healthMetric.getUnit())))
		);
		return weights;
	}

	/**
	 * 기한 내의 걸음수를 조회한다.
	 *
	 * @param startDate 조회 시작 날짜
	 * @param endDate   조회 종료 날짜
	 * @param oauthId   유저 아이디
	 * @return List<HealthMetricChartData> 걸음수 리스트
	 * @since 1.0.0
	 */
	private List<HealthMetricChartData> findStepCounts(String oauthId, LocalDate startDate, LocalDate endDate) {
		List<HealthMetricChartData> StepCounts = new ArrayList<>();

		List<HealthMetric> healthMetrics = healthMetricSearchService.findWeeklyHealthMetricById(oauthId, CommonCode.STEP_COUNT, startDate, endDate);
		healthMetrics.forEach(healthMetric ->
			StepCounts.add(new HealthMetricChartData(healthMetric.getCreatedAt(), Integer.parseInt(healthMetric.getUnit())))
		);
		return StepCounts;
	}

	/**
	 * 기한 내의 운동 소모 칼로리를 조회한다.
	 *
	 * @param startDate 조회 시작 날짜
	 * @param endDate   조회 종료 날짜
	 * @param oauthId   유저 아이디
	 * @return List<HealthMetricChartData> 칼로리 리스트
	 * @since 1.0.0
	 */
	private List<HealthMetricChartData> findExerciseCalories(String oauthId, LocalDate startDate, LocalDate endDate) {
		List<HealthMetricChartData> exerciseCalories = new ArrayList<>();
		List<ExerciseGuide> exerciseGuides = exerciseGuideSearchService.findWeekByOauthIdAndCreatedAt(oauthId, startDate, endDate);
		exerciseGuides.forEach(exerciseGuide -> {
			int calorie = exerciseGuide.getExerciseCalories().stream().mapToInt(exerciseCalorie -> exerciseCalorie.calorie()).sum();
			exerciseCalories.add(new HealthMetricChartData(exerciseGuide.getCreatedAt(), calorie));
		});
		return exerciseCalories;
	}
}
