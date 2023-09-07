package com.coniverse.dangjang.global.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * String 필드를 숫자로 converting할 수 있고 0보다 크거나 같은지 검증하는 validator
 *
 * @author TEO
 * @see NumberAndPositiveOrZero
 * @since 1.0.0
 */
public class NumberAndPositiveOrZeroValidator implements ConstraintValidator<NumberAndPositiveOrZero, String> {

	@Override
	public void initialize(NumberAndPositiveOrZero constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		try {
			int number = Integer.parseInt(value);
			return number >= 0;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
