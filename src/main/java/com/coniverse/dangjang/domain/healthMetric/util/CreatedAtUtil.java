package com.coniverse.dangjang.domain.healthMetric.util;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

/**
 * 생성일자와 관련한 util
 *
 * @author TEO
 * @since 1.0.0
 */
@Component
public class CreatedAtUtil {
	/**
	 * 생성일자를 생성한다.
	 *
	 * @param month 생성일자의 월
	 * @param day   생성일자의 일
	 * @return LocalDate 생성일자
	 * @since 1.0.0
	 */
	public LocalDate generateCreatedAt(int month, int day) {
		return LocalDate.of(LocalDate.now().getYear(), month, day);
	}
}
