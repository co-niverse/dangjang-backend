package com.coniverse.dangjang.domain.code.enums;

import java.util.Arrays;
import java.util.List;

import com.coniverse.dangjang.domain.code.exception.CodeGroupNotFoundException;
import com.coniverse.dangjang.global.support.enums.EnumCode;

import lombok.AllArgsConstructor;

/**
 * 코드 그룹
 * <p>
 * 공통 코드를 그룹으로 묶어서 관리한다.
 *
 * @author TEO
 * @since 1.0.0
 */
@AllArgsConstructor
public enum CodeGroup implements EnumCode {
	SL("수면", "sleep", List.of(CommonCode.SL_BTM, CommonCode.SL_WTM)),
	BS("혈당", "blood-sugar", List.of(
		CommonCode.BS_ESM, CommonCode.BS_BBF, CommonCode.BS_ABF, CommonCode.BS_BLC, CommonCode.BS_ALC,
		CommonCode.BS_BDN, CommonCode.BS_ADN, CommonCode.BS_BSL, CommonCode.BS_ETC)),
	BP("혈압", "blood-pressure", List.of(CommonCode.BP_SYS, CommonCode.BP_DIA)),
	EC("운동", "exercise", List.of(CommonCode.EC_HIM, CommonCode.EC_MIM, CommonCode.EC_STC)),
	WT("체중", "weight", List.of(CommonCode.WT_MEM)),
	GH("당화혈색소", "glycated-hemoglobin", List.of(CommonCode.GH_A1C));

	private final String title;
	private final String engTitle;
	private final List<CommonCode> commonCodeList;

	/**
	 * 공통 코드로 코드 그룹을 찾는다.
	 *
	 * @param commonCode 공통 코드
	 * @return CodeGroup 코드 그룹
	 * @throws CodeGroupNotFoundException 코드 그룹을 찾을 수 없을 때 발생하는 예외
	 * @see CommonCode
	 * @since 1.0.0
	 */
	public static CodeGroup findByCode(CommonCode commonCode) {
		return Arrays.stream(CodeGroup.values())
			.filter(c -> c.hasCode(commonCode))
			.findAny()
			.orElseThrow(CodeGroupNotFoundException::new);
	}

	@Override
	public String getCode() {
		return this.name();
	}

	@Override
	public String getTitle() {
		return this.engTitle;
	}

	/**
	 * 공통 코드가 코드 그룹에 속해 있는지 확인한다.
	 *
	 * @param commonCode 공통 코드
	 * @return boolean 공통 코드가 그룹에 속하는지 여부
	 * @see CommonCode
	 * @since 1.0.0
	 */
	private boolean hasCode(CommonCode commonCode) {
		return commonCodeList.stream().anyMatch(t -> t.equals(commonCode));
	}
}
