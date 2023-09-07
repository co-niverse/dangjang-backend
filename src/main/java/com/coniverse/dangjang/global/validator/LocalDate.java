package com.coniverse.dangjang.global.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * LocalDate가 유효한지 검증하는 annotation
 *
 * @author TEO
 * @see LocalDateValidator
 * @since 1.0.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LocalDateValidator.class)
public @interface LocalDate {
	String message() default "유효하지 않은 날짜입니다.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
