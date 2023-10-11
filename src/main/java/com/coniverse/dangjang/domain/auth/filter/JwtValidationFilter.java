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
import com.coniverse.dangjang.domain.auth.service.OauthLoginService;
import com.coniverse.dangjang.domain.point.service.PointService;
import com.coniverse.dangjang.domain.user.exception.NonExistentUserException;
import com.coniverse.dangjang.global.exception.BlackTokenException;
import com.coniverse.dangjang.global.exception.InvalidTokenException;
import com.coniverse.dangjang.global.support.enums.JWTStatus;

import io.jsonwebtoken.Claims;
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
	private final OauthLoginService oauthLoginService;
	private static final String AUTHORIZATION = "Authorization";
	private static final String BEARER = "Bearer";

	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws
		ServletException,
		IOException {
		String header = request.getHeader(AUTHORIZATION);
		String token = jwtTokenProvider.getToken(header);
		JWTStatus jwtStatus = jwtTokenProvider.validationToken(token);
		if (jwtStatus.equals(JWTStatus.OK)) {
			try {
				oauthLoginService.validBlackToken(token);
				Authentication auth = getAuthentication(token);
				SecurityContextHolder.getContext().setAuthentication(auth);
			} catch (BlackTokenException e) {
				request.setAttribute("exception", e.getMessage());
			}
		} else if (jwtStatus.equals(JWTStatus.EXPIRED)) {
			try {
				request.setAttribute("exception", jwtStatus.getMessage());
				request.setAttribute("accessToken", oauthLoginService.reissueToken(header));
			} catch (NonExistentUserException | InvalidTokenException notExistsError) {
				request.setAttribute("exception", notExistsError.getMessage());
			}
		} else {
			request.setAttribute("exception", jwtStatus.getMessage());
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
