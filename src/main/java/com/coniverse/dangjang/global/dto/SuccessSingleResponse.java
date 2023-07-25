package com.coniverse.dangjang.global.dto;

/**
 * 성공 응답과 함께 반환하는 객체가 하나일 때 사용하는 dto이다.
 *
 * @author TEO
 * @since 1.0.0
 */
public record SuccessSingleResponse<T>(boolean success, int errorCode, String message, T data) {
	public SuccessSingleResponse(String message, T data) {
		this(true, 0, message, data);
	}
}
