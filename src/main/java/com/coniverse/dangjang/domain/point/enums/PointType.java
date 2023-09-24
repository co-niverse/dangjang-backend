package com.coniverse.dangjang.domain.point.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 포인트 상품 타입 Enum
 *
 * @author EVE
 * @since 1.0.0
 */
@Getter
@RequiredArgsConstructor
public enum PointType {
	USE("사용"),
	EARN("적립");

	private final String description;

}
