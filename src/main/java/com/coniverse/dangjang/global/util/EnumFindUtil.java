package com.coniverse.dangjang.global.util;

import java.util.Arrays;

import com.coniverse.dangjang.global.exception.EnumNonExistentException;
import com.coniverse.dangjang.global.support.enums.EnumCode;

/**
 * Enum 관련 유틸리티
 *
 * @author TEO
 * @since 1.0.0
 */
public class EnumFindUtil {
	/**
	 * 제목으로 enum class를 찾는다.
	 *
	 * @param title 제목
	 * @return Enum
	 * @throws EnumNonExistentException enum이 존재하지 않을 때 발생하는 예외
	 * @since 1.0.0
	 */
	public static <E extends EnumCode> E findByTitle(Class<E> enumClass, String title) {
		return Arrays.stream(enumClass.getEnumConstants())
			.filter(e -> e.getTitle().equals(title))
			.findAny()
			.orElseThrow(EnumNonExistentException::new);
	}
}
