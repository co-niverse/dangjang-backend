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
import com.coniverse.dangjang.domain.guide.exercise.service.ExerciseGuideGenerateService;
import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.healthmetric.service.HealthMetricSearchService;
import com.coniverse.dangjang.global.exception.EnumNonExistentException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ExerciseAnalysisStrategy implements AnalysisStrategy {
	private final ExerciseGuideGenerateService exerciseGuideGenerateService;
	private final HealthMetricSearchService healthMetricSearchService;
	private LocalDate startDate;
	private LocalDate endDate;
	private List exerciseLsit = new ArrayList<>(
		Arrays.asList(CommonCode.EC_WLK, CommonCode.EC_RN, CommonCode.EC_HIK, CommonCode.EC_SW, CommonCode.EC_HT, CommonCode.EC_BIK));

	@Override
	public AnalysisData createAnalysisData(HealthMetric healthMetric) {
		return new ExerciseAnalysisData(healthMetric);
	}

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

		} else if (exerciseLsit.contains(data.getType())) {
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

	public void setLastWeekDate(LocalDate now) {
		int numberOfDay = now.getDayOfWeek().getValue();
		this.endDate = now.minusDays(numberOfDay);
		this.startDate = endDate.minusDays(6);
	}
}
