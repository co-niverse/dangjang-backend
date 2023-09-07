package com.coniverse.dangjang.global.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * String 필드를 숫자로 converting할 수 있고 0보다 크거나 같은지 검증하는 annotation
 *
 * @author TEO
 * @see NumberAndPositiveOrZeroValidator
 * @since 1.0.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NumberAndPositiveOrZeroValidator.class)
public @interface NumberAndPositiveOrZero {
	String message() default "유효하지 않은 숫자입니다.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
