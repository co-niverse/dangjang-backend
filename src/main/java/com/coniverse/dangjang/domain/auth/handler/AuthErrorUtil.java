package com.coniverse.dangjang.domain.auth.handler;

import java.io.IOException;

import org.springframework.http.MediaType;

import jakarta.servlet.http.HttpServletResponse;

/**
 * 인증 에러 응답을 처리하는 util
 *
 * @author TEO
 * @since 1.0.0
 */
public class AuthErrorUtil {
	/**
	 * 인증 에러 응답을 전송한다.
	 *
	 * @param response      HttpServletResponse
	 * @param status        응답 상태 코드
	 * @param errorResponse 응답 본문
	 * @since 1.0.0
	 */
	public static void sendErrorResponse(HttpServletResponse response, int status, String errorResponse) throws IOException {
		response.setStatus(status);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.addHeader("Content-Type", "application/json; charset=UTF-8");
		response.getWriter().write(errorResponse);
	}
}
