package com.coniverse.dangjang.domain.analysis.dto.healthMetric;

import java.util.List;

import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.user.entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseAnalysisData extends HealthMetricAnalysisData {
	public int unit;
	public int needStepByTTS = 0;
	public int needStepByLastWeek = 0;
	public int calorie = 0;

	public ExerciseAnalysisData(HealthMetric healthMetric) {
		super(healthMetric);
		convertUnit(healthMetric.getUnit());
		User user = healthMetric.getUser();
		CommonCode commonCode = healthMetric.getType();
	}

	@Override
	void convertUnit(String unit) {
		this.unit = Integer.parseInt(unit);
	}

	public int calculateTTSDiff(int unit) {
		int needStep = unit - 10000;
		return needStep;
	}

	public int calculateLastWeekDiff(int unit, List<HealthMetric> lastWeekHealthMetric) {

		if (lastWeekHealthMetric.isEmpty()) {
			return unit;
		}
		int sumSteps = lastWeekHealthMetric.stream()
			.mapToInt(metric -> Integer.parseInt(metric.getUnit()))
			.sum();
		int lastWeekAvg = sumSteps / lastWeekHealthMetric.size();
		return unit - lastWeekAvg;

	}

	public void setExerciseCalorie(int weight) {
		double percent = 0;
		switch (this.getType()) {
			case EC_WLK:
				percent = 0.9;
				break;
			case EC_RN:
				percent = 2;
				break;
			case EC_BIK:
				percent = 2.3;
				break;
			case EC_SW:
				percent = 2;
				break;
			case EC_HIK:
				percent = 1.5;
				break;
			case EC_HT:
				percent = 1.5;
				break;
			default:
				throw new IllegalStateException(this.getType() + ": 지원하지 않는 운동입니다.");
		}
		this.calorie = (int)(percent * weight / 15 * this.unit);
	}

}
