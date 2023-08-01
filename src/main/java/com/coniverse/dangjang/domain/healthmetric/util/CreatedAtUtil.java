package com.coniverse.dangjang.domain.healthmetric.util;

import java.time.DateTimeException;
import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.coniverse.dangjang.domain.healthmetric.exception.IncorrectCreatedAtException;

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
	 * @throws IncorrectCreatedAtException 생성일자가 올바르지 않을 때 발생하는 예외
	 * @since 1.0.0
	 */
	public LocalDate generateCreatedAt(int month, int day) {
		try {
			return LocalDate.of(LocalDate.now().getYear(), month, day);
		} catch (DateTimeException e) {
			throw new IncorrectCreatedAtException();
		}
	}
}
