package com.coniverse.dangjang.domain.point.enums;

import java.util.Arrays;

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
public enum EarnPoint {
	ACCESS(+100, "접속"),
	REGISTER(+500, "등록"),
	HEALTH_CONNECT(+500, "기기연동");
	private final int changePoint;
	private final String title;

	/**
	 * 포인트 종류
	 *
	 * @param title
	 * @return PointType
	 * @since 1.0.0
	 */
	public static EarnPoint findByTitle(String title) {
		return Arrays.stream(EarnPoint.values()).filter(type -> type.getTitle().equals(title))
			.findFirst()
			.orElseThrow(
				() -> new IllegalArgumentException("Invalid title: " + title)
			);
	}

	/**
	 * 상품 목록 조회
	 *
	 * @return PointType List
	 * @since 1.0.0
	 */

	// public static List<String> getProdfucts() {
	// 	return Arrays.asList(Arrays.stream(PointType.values())
	// 		.filter(type -> type.getChangePoint() < 0)
	// 		.map(type -> type.getTitle())
	// 		.toArray(PointType[]::new));
	// }

}
