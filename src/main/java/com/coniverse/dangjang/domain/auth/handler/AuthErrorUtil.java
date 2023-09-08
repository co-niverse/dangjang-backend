package com.coniverse.dangjang.domain.auth.handler;

import java.io.IOException;

import org.springframework.http.MediaType;

import jakarta.servlet.http.HttpServletResponse;

public class AuthErrorUtil {
	public static void sendErrorResponse(HttpServletResponse response, int status, String errorResponse) throws IOException {
		response.setStatus(status);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.addHeader("Content-Type", "application/json; charset=UTF-8");
		response.getWriter().write(errorResponse);
	}
}
