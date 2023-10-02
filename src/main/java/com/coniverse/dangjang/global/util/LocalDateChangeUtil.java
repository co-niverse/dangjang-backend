package com.coniverse.dangjang.global.util;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

/**
 * LocalDate를 UTC 또는 KST에 맞게 변환합니다.
 *
 * @author EVE
 * @since 1.0.0
 */
@Component
public class LocalDateChangeUtil {

	/**
	 * KST LocalDate를 UTC에 맞게 변환합니다.
	 *
	 * @param createdAt KST 날짜
	 * @return LocalDate UTC 날짜
	 * @since 1.0.0
	 */
	public static LocalDate convertDateToUTC(LocalDate createdAt) {
		return createdAt.plusDays(1);
	}

	/**
	 * UTC LocalDate를 KST에 맞게 변환합니다.
	 *
	 * @param createdAt UTC 날짜
	 * @return LocalDate KST 날짜
	 * @since 1.0.0
	 */
	public static LocalDate convertDateToKST(LocalDate createdAt) {
		return createdAt.minusDays(1);
	}

}
