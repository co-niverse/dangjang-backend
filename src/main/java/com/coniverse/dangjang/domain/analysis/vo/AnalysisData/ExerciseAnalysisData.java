package com.coniverse.dangjang.domain.analysis.vo.AnalysisData;

import java.time.LocalDate;
import java.util.List;

import com.coniverse.dangjang.domain.analysis.enums.GuideSign;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.healthmetric.service.HealthMetricSearchService;
import com.coniverse.dangjang.domain.user.entity.User;

public class ExerciseAnalysisData extends AbstractAnalysisData {
	private HealthMetricSearchService healthMetricSearchService;
	public int unit;
	public int needStepByTTS = 0;
	public GuideSign signByTTS = GuideSign.positive;
	public int needStepByLastWeek = 0;
	public int calorie = 0;

	public ExerciseAnalysisData(HealthMetric healthMetric, User user, HealthMetricSearchService healthMetricSearchService) {
		super(healthMetric.getCreatedAt(), healthMetric.getCommonCode(), user.getOauthId());
		this.healthMetricSearchService = healthMetricSearchService;
		convertUnit(healthMetric.getUnit());
		CommonCode commonCode = healthMetric.getCommonCode();
		if (commonCode.equals(CommonCode.EC_STC)) {
			setTTSDiff(this.unit);
			setLastWeekDiff(this.unit, healthMetric, user.getOauthId());
		} else if (commonCode.equals(CommonCode.EC_WLK) || commonCode.equals(CommonCode.EC_RN) || commonCode.equals(CommonCode.EC_HIK) || commonCode.equals(
			CommonCode.EC_SW) || commonCode.equals(CommonCode.EC_HT)) {
			String weight = healthMetricSearchService.findLastHealthMetricById(user.getOauthId(), CommonCode.WT_MEM).getUnit();
			setExerciseCalorie(commonCode, unit, Integer.parseInt(weight));
		}

	}

	@Override
	void convertUnit(String unit) {
		this.unit = Integer.parseInt(unit);
	}

	void setTTSDiff(int unit) {
		int needStep = unit - 10000;
		this.signByTTS = GuideSign.of(needStep);
		this.needStepByTTS = Math.abs(needStep);
	}

	void setLastWeekDiff(int unit, HealthMetric healthMetric, String oauthId) {
		int numberOfDay = healthMetric.getCreatedAt().getDayOfWeek().getValue();
		LocalDate lastWeekSunday = healthMetric.getCreatedAt().minusDays(numberOfDay);
		LocalDate lastWeekMonday = lastWeekSunday.minusDays(6);

		List<HealthMetric> lastWeekHealthMetric = healthMetricSearchService.findLastWeekHealthMetricById(oauthId, healthMetric.getCommonCode(), lastWeekMonday,
			lastWeekSunday);
		if (lastWeekHealthMetric.isEmpty()) {
			this.needStepByLastWeek = unit;
		} else {
			int sumSteps = lastWeekHealthMetric.stream()
				.mapToInt(metric -> Integer.parseInt(metric.getUnit()))
				.sum();
			int lastWeekAvg = sumSteps / lastWeekHealthMetric.size();
			this.needStepByLastWeek = unit - lastWeekAvg;
		}
	}

	void setExerciseCalorie(CommonCode commonCode, int unit, int weight) {
		double percent = 0;
		switch (commonCode) {
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
				throw new IllegalStateException(commonCode + ": 지원하지 않는 운동입니다.");
		}
		this.calorie = (int)(percent * weight / 15 * unit);
	}

}
