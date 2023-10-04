package com.coniverse.dangjang.domain.auth.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Stream;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.coniverse.dangjang.domain.auth.service.JwtTokenProvider;
import com.coniverse.dangjang.domain.point.service.PointService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * JWT 검증 필터
 *
 * @author EVE
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class JwtValidationFilter extends OncePerRequestFilter {
	private final PointService pointService;
	private final JwtTokenProvider jwtTokenProvider;
	private static final String AUTHORIZATION = "Authorization";
	private static final String BEARER = "Bearer";

	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws
		ServletException,
		IOException {
		String token = jwtTokenProvider.getToken(request.getHeader(AUTHORIZATION));

		if (jwtTokenProvider.validationToken(token)) {
			Authentication auth = getAuthentication(token);
			SecurityContextHolder.getContext().setAuthentication(auth);
		} else {
			throw new JwtException("토큰이 만료되었습니다.");
		}

		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String authorization = request.getHeader(AUTHORIZATION);
		return authorization == null || !authorization.startsWith(BEARER);
	}

	/**
	 * Authentication 을 생성한다
	 *
	 * @param token
	 * @return Authentication 접근한 사용자 정보
	 * @since 1.0.0
	 */
	private Authentication getAuthentication(String token) {
		Claims claims = jwtTokenProvider.parseClaims(token);

		String oauthId = claims.getSubject();

		final Collection<? extends GrantedAuthority> authorities = Stream.of(
				claims.get("role").toString())
			.map(SimpleGrantedAuthority::new)
			.toList();

		User principal = new User(claims.getSubject(), oauthId, authorities);
		checkAccessPoint(oauthId);
		return new UsernamePasswordAuthenticationToken(principal, oauthId, authorities);
	}

	/**
	 * 접속 포인트 적립
	 *
	 * @param oauthId
	 * @since 1.0.0
	 */

	private void checkAccessPoint(String oauthId) {
		pointService.addAccessPoint(oauthId);
	}
}
