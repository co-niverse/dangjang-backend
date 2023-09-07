package com.coniverse.dangjang.domain.code.enums;

import com.coniverse.dangjang.global.support.enums.EnumCode;

import lombok.AllArgsConstructor;

/**
 * 공통 코드
 * <p>
 * 코드 그룹 prefix_ + 코드명으로 구성된다.
 *
 * @author TEO
 * @since 1.0.0
 */
@AllArgsConstructor
public enum CommonCode implements EnumCode {
	SL_BTM("취침시간", "bed time"),
	SL_WTM("기상시간", "wake up time"),
	BS_ESM("공복", "empty stomach"),
	BS_BBF("아침식전", "before breakfast"),
	BS_ABF("아침식후", "after breakfast"),
	BS_BLC("점심식전", "before lunch"),
	BS_ALC("점심식후", "after lunch"),
	BS_BDN("저녁식전", "before dinner"),
	BS_ADN("저녁식후", "after dinner"),
	BS_BSL("취침전", "before sleep"),
	BS_ETC("기타", "etc"),
	BP_SYS("수축기", "systolic"),
	BP_DIA("이완기", "diastolic"),
	EC_WLK("걷기", "walk"),
	EC_RN("달리기", "run"),
	EC_HIK("하이킹", "hiking"),
	EC_BIK("자전거", "bike"),
	EC_SW("수영", "swim"),
	EC_HT("헬스", "health"),
	EC_STC("걸음수", "step count"),
	WT_MEM("체중", "measurement"),
	GH_A1C("당화혈색소", "HBA1C");

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
