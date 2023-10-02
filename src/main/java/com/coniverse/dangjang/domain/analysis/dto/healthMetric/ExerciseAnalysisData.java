package com.coniverse.dangjang.domain.analysis.dto.healthMetric;

import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;

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
	}

	@Override
	void convertUnit(String unit) {
		this.unit = Integer.parseInt(unit);
	}
}
