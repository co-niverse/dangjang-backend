package com.coniverse.dangjang.domain.analysis.strategy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.coniverse.dangjang.domain.analysis.dto.AnalysisData;
import com.coniverse.dangjang.domain.analysis.dto.healthMetric.ExerciseAnalysisData;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.code.enums.GroupCode;
import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.healthmetric.service.HealthMetricSearchService;
import com.coniverse.dangjang.global.exception.EnumNonExistentException;

import lombok.RequiredArgsConstructor;

/**
 * 운동 분석 전략
 *
 * @author EVE
 * @see ExerciseAnalysisData
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class ExerciseAnalysisStrategy implements AnalysisStrategy {
	private final HealthMetricSearchService healthMetricSearchService;
	private LocalDate startDate;
	private LocalDate endDate;
	private List exerciseList = new ArrayList<>(
		Arrays.asList(CommonCode.WALK, CommonCode.RUN, CommonCode.HIKING, CommonCode.SWIM, CommonCode.HEALTH, CommonCode.BIKE));

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

			int needStepByTTS = data.calculateTTSDiff(data.unit);
			data.setNeedStepByTTS(needStepByTTS);

			setLastWeekDate(data.getCreatedAt());
			List<HealthMetric> lastWeekHealthMetricList = healthMetricSearchService.findLastWeekHealthMetricById(data.getOauthId(), data.getType(),
				this.startDate, this.endDate);
			int needStepByLastWeek = data.calculateLastWeekDiff(data.unit, lastWeekHealthMetricList);
			data.setNeedStepByLastWeek(needStepByLastWeek);

		} else if (exerciseList.contains(data.getType())) {
			String weight = healthMetricSearchService.findLastHealthMetricById(data.getOauthId(), CommonCode.MEASUREMENT).getUnit();
			data.setExerciseCalorie(Integer.parseInt(weight));
		} else {
			throw new EnumNonExistentException();
		}
		return data;
	}

	@Override
	public GroupCode getGroupCode() {
		return GroupCode.EXERCISE;
	}

	/**
	 * 저번주 월요일, 일요일을 구한다.
	 *
	 * @param now 현재 날짜
	 * @since 1.0.0
	 */
	public void setLastWeekDate(LocalDate now) {
		int numberOfDay = now.getDayOfWeek().getValue();
		this.endDate = now.minusDays(numberOfDay);
		this.startDate = endDate.minusDays(6);
	}
}
