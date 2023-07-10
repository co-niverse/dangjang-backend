package com.coniverse.dangjang.global.dto;

import java.util.List;
import java.util.Set;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

/**
 * 공통된 에러 응답을 보내기 위해 사용한다.
 *
 * @author Teo
 * @since 1.0
 */
public record ErrorResponse(boolean success, int errorCode, String message,
							@JsonInclude(JsonInclude.Include.NON_NULL) List<FieldError> fieldErrors,
							@JsonInclude(JsonInclude.Include.NON_NULL) List<ConstraintViolationError> violationErrors) {
	public ErrorResponse(int errorCode, String message) {
		this(false, errorCode, message, null, null);
	}

	public ErrorResponse(int errorCode, String message, BindingResult bindingResult) {
		this(false, errorCode, message, FieldError.of(bindingResult), null);
	}

	public ErrorResponse(int errorCode, String message, Set<ConstraintViolation<?>> violationErrors) {
		this(false, errorCode, message, null, ConstraintViolationError.of(violationErrors));
	}

	/**
	 * {@link MethodArgumentNotValidException}에서 발생한 fieldError를 binding한다.
	 *
	 * @since 1.0
	 */
	private record FieldError(String field, Object rejectedValue, String reason) {
		private static List<FieldError> of(BindingResult bindingResult) {
			return bindingResult.getFieldErrors().stream()
				.map(error -> new FieldError(
					error.getField(),
					error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
					error.getDefaultMessage()))
				.toList();
		}
	}

	/**
	 * {@link ConstraintViolationException}에서 발생한 constraintViolation을 binding한다.
	 *
	 * @since 1.0
	 */
	private record ConstraintViolationError(String propertyPath, Object rejectedValue, String reason) {
		private static List<ConstraintViolationError> of(Set<ConstraintViolation<?>> constraintViolations) {
			return constraintViolations.stream()
				.map(constraintViolation -> new ConstraintViolationError(
					constraintViolation.getPropertyPath().toString(),
					constraintViolation.getInvalidValue().toString(),
					constraintViolation.getMessage()))
				.toList();
		}
	}
}
