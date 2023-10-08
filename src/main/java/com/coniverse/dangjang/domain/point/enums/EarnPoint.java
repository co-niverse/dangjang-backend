package com.coniverse.dangjang.domain.point.enums;

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
	ACCESS(100, "접속"),
	REGISTER(500, "등록"),
	HEALTH_CONNECT(500, "기기연동");
	private final int changePoint;
	private final String title;
}