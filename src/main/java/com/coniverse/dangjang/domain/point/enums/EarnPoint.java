package com.coniverse.dangjang.domain.point.enums;

import com.coniverse.dangjang.domain.code.enums.GroupCode;
import com.coniverse.dangjang.domain.healthmetric.exception.HealthMetricNotFoundException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 포인트 적립 종류 Enum
 *
 * @author EVE
 * @since 1.0.0
 */
@Getter
@RequiredArgsConstructor
public enum EarnPoint {
	ACCESS(500, "접속"),
	REGISTER(500, "등록"),
	HEALTH_CONNECT(500, "기기연동"),
	BLOOD_SUGAR(300, "혈당"),
	EXERCISE(200, "운동"),
	WEIGHT(200, "체중");
	private final int changePoint;
	private final String title;

	/**
	 * 그룹 코드에 따른 포인트 적립 종류를 반환한다.
	 *
	 * @return EarnPoint 포인트 적립 종류
	 * @throws HealthMetricNotFoundException 건강지표를 찾을 수 없을 때 발생하는 예외
	 * @author EVE
	 * @since 1.3.0
	 */
	public static EarnPoint findByGroupCode(GroupCode groupCode) {
		return switch (groupCode) {
			case BLOOD_SUGAR -> EarnPoint.BLOOD_SUGAR;
			case EXERCISE -> EarnPoint.EXERCISE;
			case WEIGHT -> EarnPoint.WEIGHT;
			default -> throw new HealthMetricNotFoundException();
		};
	}

}
