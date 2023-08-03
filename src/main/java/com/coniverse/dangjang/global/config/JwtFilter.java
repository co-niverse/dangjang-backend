package com.coniverse.dangjang.global.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.coniverse.dangjang.domain.auth.service.JwtTokenProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
	private final JwtTokenProvider jwtTokenProvider;

	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws
		ServletException,
		IOException {
		String headerSubstring = getToken(request.getHeader("Authorization"));
		if (headerSubstring != null) {
			final String token = headerSubstring;
			if (token != null && jwtTokenProvider.validationToken(token)) {
				Authentication auth = jwtTokenProvider.getAuthentication(token);
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}
		filterChain.doFilter(request, response);
	}

	private String getToken(final String authHeader) {
		if (StringUtils.hasLength(authHeader) && authHeader.startsWith("Bearer")) {
			System.out.println("JwtFilter + getToken: " + authHeader.substring(7));
			return authHeader.substring(7);
		}
		return null;

	}
}
