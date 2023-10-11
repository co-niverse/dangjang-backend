package com.coniverse.dangjang.domain.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coniverse.dangjang.domain.auth.dto.request.KakaoLoginRequest;
import com.coniverse.dangjang.domain.auth.dto.request.NaverLoginRequest;
import com.coniverse.dangjang.domain.auth.dto.response.LoginResponse;
import com.coniverse.dangjang.domain.auth.service.OauthLoginService;
import com.coniverse.dangjang.global.dto.SuccessSingleResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * 로그인 컨트롤러
 *
 * @author EVE
 * @since 1.0.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class LoginController {
	private final OauthLoginService oauthLoginService;
	private final String headerKeyFcmToken = "FcmToken";
	private final String headerKeyAccessToken = "AccessToken";
	private final String headerKeyAuthorization = "Authorization";

	/**
	 * @param params  카카오 accessToken
	 * @param request request에서 fcmToken header가 필요
	 * @return ResponseEntity 로그인을 성공하면, JWT TOKEN과 사용자 정보(nickname, auth id)를 전달한다.
	 * @since 1.0.0
	 */
	@PostMapping("/kakao")
	public ResponseEntity<SuccessSingleResponse<LoginResponse>> loginKakao(@Valid @RequestBody KakaoLoginRequest params, HttpServletRequest request) {
		LoginResponse loginResponse = oauthLoginService.login(params, request.getHeader(headerKeyFcmToken));
		String accessToken = oauthLoginService.getAuthToken(loginResponse.nickname());
		return ResponseEntity.ok()
			.header(headerKeyAccessToken, accessToken)
			.body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), loginResponse));
	}

	/**
	 * @param params  네이버 accessToken
	 * @param request request에서 fcmToken header가 필요
	 * @return ResponseEntity 로그인을 성공하면, JWT TOKEN과 사용자 정보(nickname, auth id)를 전달한다.
	 * @since 1.0.0
	 */
	@PostMapping("/naver")
	public ResponseEntity<SuccessSingleResponse<LoginResponse>> loginNaver(@Valid @RequestBody NaverLoginRequest params, HttpServletRequest request) {
		LoginResponse loginResponse = oauthLoginService.login(params, request.getHeader(headerKeyFcmToken));
		String accessToken = oauthLoginService.getAuthToken(loginResponse.nickname());
		return ResponseEntity.ok()
			.header(headerKeyAccessToken, accessToken)
			.body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), loginResponse));
	}

	/**
	 * refreshToken으로 AuthToken 재발급
	 *
	 * @param request 요청
	 * @return AuthToken
	 * @since 1.0.0
	 */
	@PostMapping("/reissue")
	public ResponseEntity<SuccessSingleResponse<?>> reissue(HttpServletRequest request) {
		String newAccessToken = oauthLoginService.reissueToken(request.getHeader(headerKeyAuthorization));
		return ResponseEntity.ok()
			.header(headerKeyAccessToken, newAccessToken)
			.body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), null));
	}

	/**
	 * 로그아웃
	 *
	 * @param request 요청
	 * @since 1.1.0
	 */
	@PostMapping("/logout")
	public ResponseEntity<SuccessSingleResponse> logout(HttpServletRequest request) {
		oauthLoginService.logout(request.getHeader("Authorization"), request.getHeader(headerKeyFcmToken));
		return ResponseEntity.ok()
			.body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), null));
	}
}
