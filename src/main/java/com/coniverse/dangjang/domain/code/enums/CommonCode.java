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
	BED_TIME("취침시간", "bed time"),
	WAKE_UP_TIME("기상시간", "wake up time"),
	EMPTY_STOMACH("공복", "empty stomach"),
	BEFORE_BREAKFAST("아침식전", "before breakfast"),
	AFTER_BREAKFAST("아침식후", "after breakfast"),
	BEFORE_LUNCH("점심식전", "before lunch"),
	AFTER_LUNCH("점심식후", "after lunch"),
	BEFORE_DINNER("저녁식전", "before dinner"),
	AFTER_DINNER("저녁식후", "after dinner"),
	BEFORE_SLEEP("취침전", "before sleep"),
	ETC("기타", "etc"),
	SYSTOLIC("수축기", "systolic"),
	DIASTOLIC("이완기", "diastolic"),
	HIGH_INTENSITY_MINUTES("고강도운동", "high intensity minutes"),
	MIN_INTENSITY_MINUTES("중강도운동", "moderate intensity minutes"),
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
