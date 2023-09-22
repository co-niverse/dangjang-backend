package com.coniverse.dangjang.domain.point.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 포인트 종류
 *
 * @author EVE
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum PointType {
	ACCESS(+100),
	REGISTER(+500),
	HEALTH_CONNECT(+500),
	PURCHASE(-5000);
	private final int changePoint;
}
