package com.coniverse.dangjang.domain.guide.exercise.convert;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;

/**
 * LocalDate를 UTC 또는 KST에 맞게 변환합니다.
 *
 * @author EVE
 * @since 1.0.0
 */
public class LocalDateConverter implements Converter<LocalDate, LocalDate> {

	/**
	 * KST LocalDate를 UTC에 맞게 변환합니다.
	 *
	 * @param createdAt KST 날짜
	 * @return LocalDate UTC 날짜
	 * @since 1.0.0
	 */
	public LocalDate convertDateToUTC(LocalDate createdAt) {
		return createdAt.plusDays(1);
	}

	/**
	 * UTC LocalDate를 KST에 맞게 변환합니다.
	 *
	 * @param createdAt UTC 날짜
	 * @return LocalDate KST 날짜
	 * @since 1.0.0
	 */
	public LocalDate convertDateKST(LocalDate createdAt) {
		return createdAt.minusDays(1);
	}

	@Override
	public LocalDate convert(LocalDate value) {
		return value;
	}

	@Override
	public JavaType getInputType(TypeFactory typeFactory) {
		return null;
	}

	@Override
	public JavaType getOutputType(TypeFactory typeFactory) {
		return null;
	}
}
