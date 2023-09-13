package com.coniverse.dangjang.domain.auth.handler;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.coniverse.dangjang.global.dto.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 인증이 완료되었지만 권한이 없는 endpoint에 접근할 경우 발생하는 exception handler
 *
 * @author TEO
 * @since 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
	private final ObjectMapper objectMapper;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
		log.error("접근 권한이 없는 요청 발생 : {}", accessDeniedException.getMessage());
		log.error("요청 URL : {}", request.getRequestURI());

		int status = HttpStatus.FORBIDDEN.value();
		String errorResponse = objectMapper.writeValueAsString(new ErrorResponse(status, "접근 권한이 없습니다."));
		AuthErrorUtil.sendErrorResponse(response, status, errorResponse);
	}
}
