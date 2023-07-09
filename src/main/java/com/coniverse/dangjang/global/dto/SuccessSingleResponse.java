package com.coniverse.dangjang.global.dto;

/**
 * 공통된 성공 응답을 보내기 위해 사용한다.
 *
 * @author Teo
 * @since 1.0
 */
public record SuccessSingleResponse<T>(boolean success, int errorCode, String message, T data) {
	public SuccessSingleResponse(String message, T data) {
		this(true, 0, message, data);
	}
}
