package com.coniverse.dangjang.domain.analysis.vo.AnalysisData;

import com.coniverse.dangjang.domain.analysis.enums.GuideSign;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.user.entity.User;

public class ExerciseAnalysisData extends AbstractAnalysisData {
	public int unit;
	public int needStepByTTS;
	public GuideSign signByTTS;

	public ExerciseAnalysisData(HealthMetric healthMetric, User user) {
		super(healthMetric.getCreatedAt(), healthMetric.getCommonCode(), user.getOauthId());

		convertUnit(healthMetric.getUnit());
		if (healthMetric.getCommonCode().equals(CommonCode.EC_STC)) {
			calculateByTTS(this.unit);
		}
	}

	@Override
	void convertUnit(String unit) {
		this.unit = Integer.parseInt(unit);
	}

	void calculateByTTS(Integer unit) {
		int needStep = unit - 10000;
		this.signByTTS = GuideSign.of(needStep);
		this.needStepByTTS = Math.abs(needStep);
	}

}
