package com.coniverse.dangjang.domain.analysis.dto.healthMetric;

import com.coniverse.dangjang.domain.analysis.enums.Alert;
import com.coniverse.dangjang.domain.analysis.exception.UnitLessThanZeroException;
import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.user.entity.User;

import lombok.Getter;

/**
 * 혈당 분석 데이터
 *
 * @author TEO
 * @since 1.0.0
 */
@Getter
public class BloodSugarAnalysisData extends HealthMetricAnalysisData {
	private final boolean diabetic;
	private final boolean medicine;
	private final boolean injection;
	private int unit;
	private Alert alert;
	private int deviation;
	private boolean lackOfExercise;

	public BloodSugarAnalysisData(HealthMetric healthMetric, User user) {
		super(user.getOauthId(), healthMetric.getCreatedAt(), healthMetric.getType());
		this.diabetic = true; // TODO 유저 정보 mapping
		this.medicine = true;
		this.injection = true;
	}

	@Override
	public void convertUnit(String unit) {
		try {
			this.unit = Integer.parseInt(unit);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("혈당 단위가 숫자가 아닙니다.");
		}
		if (this.unit < 0) {
			throw new UnitLessThanZeroException();
		}
	}

	public void setAlert(Alert alert) {
		this.alert = alert;
	}

	public void setDeviation(int deviation) {
		this.deviation = deviation;
	}

	public void setLackOfExercise(boolean lackOfExercise) {
		this.lackOfExercise = lackOfExercise;
	}
}
