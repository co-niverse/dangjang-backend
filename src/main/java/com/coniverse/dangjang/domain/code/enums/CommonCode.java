package com.coniverse.dangjang.domain.code.enums;

import com.coniverse.dangjang.global.support.enums.EnumCode;

import lombok.AllArgsConstructor;

/**
 * 공통 코드
 *
 * @author TEO
 * @since 1.0.0
 */
@AllArgsConstructor
public enum CommonCode implements EnumCode {
	EMPTY_STOMACH("공복", "empty stomach"),
	BEFORE_BREAKFAST("아침식전", "before breakfast"),
	AFTER_BREAKFAST("아침식후", "after breakfast"),
	BEFORE_LUNCH("점심식전", "before lunch"),
	AFTER_LUNCH("점심식후", "after lunch"),
	BEFORE_DINNER("저녁식전", "before dinner"),
	AFTER_DINNER("저녁식후", "after dinner"),
	BEFORE_SLEEP("취침전", "before sleep"),
	ETC("기타", "etc"),
	STEP_COUNT("걸음수", "step count"),
	MEASUREMENT("체중", "measurement"),
	HBA1C("당화혈색소", "HBA1C");

	private final String title;
	private final String engTitle;

	@Override
	public String getCode() {
		return this.name();
	}

	@Override
	public String getTitle() {
		return this.title;
	}
}
