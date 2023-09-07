package com.coniverse.dangjang.global.validator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * LocalDate가 유효한지 검증하는 validator
 *
 * @author TEO
 * @see LocalDateValid
 * @since 1.0.0
 */
public class LocalDateValidator implements ConstraintValidator<LocalDateValid, String> {
	@Override
	public void initialize(LocalDateValid constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(String date, ConstraintValidatorContext context) {
		try {
			LocalDate.parse(date);
		} catch (DateTimeParseException e) {
			return false;
		}
		return true;
	}
}
