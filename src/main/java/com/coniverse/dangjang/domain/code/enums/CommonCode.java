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
	EC_WLK("걷기", "walk"),
	EC_RN("달리기", "run"),
	EC_HIK("하이킹", "hiking"),
	EC_BIK("자전거", "bike"),
	EC_SW("수영", "swim"),
	EC_HT("헬스", "health"),
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
