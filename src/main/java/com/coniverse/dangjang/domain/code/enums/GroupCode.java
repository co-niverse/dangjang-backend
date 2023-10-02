package com.coniverse.dangjang.domain.code.enums;

import java.util.Arrays;
import java.util.List;

import com.coniverse.dangjang.domain.code.exception.GroupCodeNotFoundException;
import com.coniverse.dangjang.global.support.enums.EnumCode;

import lombok.AllArgsConstructor;

/**
 * 그룹 코드
 * <p>
 * 공통 코드를 그룹으로 묶어서 관리한다.
 *
 * @author TEO
 * @since 1.0.0
 */
@AllArgsConstructor
public enum GroupCode implements EnumCode {
	BLOOD_SUGAR("혈당", "blood sugar", List.of(
		CommonCode.EMPTY_STOMACH, CommonCode.BEFORE_BREAKFAST, CommonCode.AFTER_BREAKFAST, CommonCode.BEFORE_LUNCH, CommonCode.AFTER_LUNCH,
		CommonCode.BEFORE_DINNER, CommonCode.AFTER_DINNER, CommonCode.BEFORE_SLEEP, CommonCode.ETC)
	),
	EXERCISE("운동", "exercise",
		List.of(CommonCode.STEP_COUNT, CommonCode.HEALTH, CommonCode.BIKE, CommonCode.HIKING, CommonCode.RUN, CommonCode.SWIM, CommonCode.WALK)),
	WEIGHT("체중", "weight", List.of(CommonCode.MEASUREMENT)),
	GLYCATED_HEMOGLOBIN("당화혈색소", "glycated hemoglobin", List.of(CommonCode.HBA1C));

	private final String title;
	private final String engTitle;
	private final List<CommonCode> commonCodeList;

	/**
	 * 공통 코드로 코드 그룹을 찾는다.
	 *
	 * @param commonCode 공통 코드
	 * @return GroupCode 그룹 코드
	 * @throws GroupCodeNotFoundException 그룹 코드를 찾을 수 없을 때 발생하는 예외
	 * @see CommonCode
	 * @since 1.0.0
	 */
	public static GroupCode findByCode(CommonCode commonCode) {
		return Arrays.stream(GroupCode.values())
			.filter(c -> c.hasCode(commonCode))
			.findAny()
			.orElseThrow(GroupCodeNotFoundException::new);
	}

	@Override
	public String getCode() {
		return this.name();
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	/**
	 * 공통 코드가 그룹 코드에 속해 있는지 확인한다.
	 *
	 * @param commonCode 공통 코드
	 * @return boolean 공통 코드가 그룹에 속하는지 여부
	 * @see CommonCode
	 * @since 1.0.0
	 */
	public boolean hasCode(CommonCode commonCode) {
		return commonCodeList.stream().anyMatch(t -> t.equals(commonCode));
	}
}
