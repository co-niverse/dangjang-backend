package com.coniverse.dangjang.domain.auth.handler;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.coniverse.dangjang.global.dto.ErrorResponse;
import com.coniverse.dangjang.global.dto.JwtExpiredResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 익명 사용자가 인증이 필요한 endpoint에 접근할 경우 발생하는 exception handler
 *
 * @author TEO
 * @since 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	private final ObjectMapper objectMapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
		log.error("승인되지 않은 요청 발생 : {}", authException.getMessage());
		log.error("요청 URL : {}", request.getRequestURI());

		int status = HttpStatus.UNAUTHORIZED.value();
		String customResponse;
		try {
			customResponse = objectMapper.writeValueAsString(new JwtExpiredResponse());
			response.addHeader("accessToken", request.getAttribute("accessToken").toString());
			status = HttpStatus.OK.value();
		} catch (NullPointerException accessTokenNullError) {
			try {
				customResponse = objectMapper.writeValueAsString(new ErrorResponse(status, request.getAttribute("exception").toString()));
			} catch (NullPointerException exceptionNullError) {
				customResponse = objectMapper.writeValueAsString(new ErrorResponse(status, "로그인이 필요합니다."));
			}
		}
		AuthErrorUtil.sendErrorResponse(response, status, customResponse);
	}
}
