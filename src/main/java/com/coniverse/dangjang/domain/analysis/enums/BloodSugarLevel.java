package com.coniverse.dangjang.domain.analysis.enums;

import com.coniverse.dangjang.domain.code.enums.CommonCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 전단계 당뇨환자와 2형 당뇨환자의 식전, 식후 혈당 정상 수치 기준
 * <p>
 * 전단계 당뇨환자: 공복 혈당 70~99mg/dL, 식후 혈당 90~139mg/dL
 * <p>
 * 2형 당뇨환자: 공복 혈당 80~129mg/dL, 식후 혈당 90~179mg/dL
 *
 * @author TEO
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum BloodSugarLevel {
	PRE_DIABETES_BM(false, Meal.BEFORE_MEAL, 70, 99, 126),
	PRE_DIABETES_AM(false, Meal.AFTER_MEAL, 90, 139, 200),
	DIABETES_BM(true, Meal.BEFORE_MEAL, 80, 129, 150),
	DIABETES_AM(true, Meal.AFTER_MEAL, 90, 179, 230);

	private final boolean diabetic;
	private final Meal meal;
	private final int normalMinCriteria;
	private final int normalMaxCriteria;
	private final int cautionCriteria;

	public boolean contains(CommonCode bloodSugarType) {
		return this.meal.contains(bloodSugarType);
	}
}
