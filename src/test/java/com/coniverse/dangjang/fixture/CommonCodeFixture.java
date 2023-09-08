package com.coniverse.dangjang.fixture;

import java.util.List;

import com.coniverse.dangjang.domain.code.enums.CommonCode;

/**
 * 공통 코드 fixture
 *
 * @author TEO
 * @since 1.0.0
 */
public class CommonCodeFixture {
	public static List<CommonCode> 혈당_타입() {
		return List.of(
			CommonCode.EMPTY_STOMACH,
			CommonCode.BEFORE_BREAKFAST,
			CommonCode.AFTER_BREAKFAST,
			CommonCode.BEFORE_LUNCH,
			CommonCode.AFTER_LUNCH,
			CommonCode.BEFORE_DINNER,
			CommonCode.AFTER_DINNER,
			CommonCode.BEFORE_SLEEP,
			CommonCode.ETC
		);
	}

	public static List<CommonCode> 운동_타입() {
		return List.of(
			CommonCode.STEP_COUNT
		);
	}

	public static List<CommonCode> 체중_타입() {
		return List.of(
			CommonCode.MEASUREMENT
		);
	}

	public static List<CommonCode> 당화혈색소_타입() {
		return List.of(
			CommonCode.HBA1C
		);
	}
}
