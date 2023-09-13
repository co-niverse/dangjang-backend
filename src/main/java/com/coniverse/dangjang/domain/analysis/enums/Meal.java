package com.coniverse.dangjang.domain.analysis.enums;

import java.util.List;

import com.coniverse.dangjang.domain.code.enums.CommonCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 혈당 타입의 식전, 식후 구분
 *
 * @author TEO
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum Meal {
	BEFORE_MEAL(List.of(CommonCode.EMPTY_STOMACH, CommonCode.BEFORE_BREAKFAST, CommonCode.BEFORE_LUNCH, CommonCode.BEFORE_DINNER)),
	AFTER_MEAL(List.of(CommonCode.AFTER_BREAKFAST, CommonCode.AFTER_LUNCH, CommonCode.AFTER_DINNER, CommonCode.BEFORE_SLEEP, CommonCode.ETC));

	private final List<CommonCode> bloodSugarTypes;

	public boolean contains(CommonCode bloodSugarType) {
		return this.bloodSugarTypes.contains(bloodSugarType);
	}
}

