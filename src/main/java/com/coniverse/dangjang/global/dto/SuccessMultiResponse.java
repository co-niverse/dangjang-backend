package com.coniverse.dangjang.global.dto;

import java.util.List;

/**
 * 성공 응답과 함께 반환하는 객체가 여러 개일 때 사용하는 dto이다.
 *
 * @author TEO
 * @since 1.0.0
 */
public record SuccessMultiResponse<T>(boolean success, int errorCode, String message, List<T> data) {
	public SuccessMultiResponse(String message, List<T> data) {
		this(true, 0, message, data);
	}
}
