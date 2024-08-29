package com.coniverse.dangjang.domain.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coniverse.dangjang.domain.auth.dto.request.KakaoLoginRequest;
import com.coniverse.dangjang.domain.auth.dto.request.LogoutFcmTokenRequest;
import com.coniverse.dangjang.domain.auth.dto.request.NaverLoginRequest;
import com.coniverse.dangjang.domain.auth.dto.response.LoginResponse;
import com.coniverse.dangjang.domain.auth.service.OauthLoginService;
import com.coniverse.dangjang.global.dto.SuccessSingleResponse;

import given.apiversion.core.annotation.ApiVersion;
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
@RequestMapping("/auth")
public class LoginController {
	private static final String ACCESS_TOKEN = "AccessToken";
	private static final String AUTHORIZATION = "Authorization";
	private final OauthLoginService oauthLoginService;

	/**
	 * 카카오 로그인
	 *
	 * @param params 카카오 access token
	 * @return jwt token, 로그인 정보
	 * @since 1.6.0
	 */
	@ApiVersion("1")
	@PostMapping("/kakao")
	public ResponseEntity<SuccessSingleResponse<LoginResponse>> loginKakaoV1(@Valid @RequestBody KakaoLoginRequest params) {
		LoginResponse loginResponse = oauthLoginService.login(params);
		String accessToken = oauthLoginService.getAuthToken(loginResponse.nickname());
		return ResponseEntity.ok()
			.header(ACCESS_TOKEN.toString(), accessToken)
			.body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), loginResponse));
	}

	/**
	 * 네이버 로그인
	 *
	 * @param params 네이버 access token
	 * @return jwt token, 로그인 정보
	 * @since 1.6.0
	 */
	@ApiVersion("1")
	@PostMapping("/naver")
	public ResponseEntity<SuccessSingleResponse<LoginResponse>> loginNaverV1(@Valid @RequestBody NaverLoginRequest params) {
		LoginResponse loginResponse = oauthLoginService.login(params);
		String accessToken = oauthLoginService.getAuthToken(loginResponse.nickname());
		return ResponseEntity.ok()
			.header(ACCESS_TOKEN, accessToken)
			.body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), loginResponse));
	}

	/**
	 * refresh token으로 access token 재발급
	 *
	 * @param request 재발급 요청, header에 access token 필요
	 * @return AuthToken
	 * @since 1.6.0
	 */
	@ApiVersion("1")
	@PostMapping("/reissue")
	public ResponseEntity<SuccessSingleResponse<?>> reissueV1(HttpServletRequest request) {
		String newAccessToken = oauthLoginService.reissueToken(request.getHeader(AUTHORIZATION));
		return ResponseEntity.ok()
			.header(ACCESS_TOKEN, newAccessToken)
			.body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), null));
	}

	/**
	 * 로그아웃
	 *
	 * @param request               HttpServletRequest
	 * @param logoutFcmTokenRequest fcm token 정보
	 * @since 1.6.0
	 */
	@ApiVersion("1")
	@PostMapping("/logout")
	public ResponseEntity<SuccessSingleResponse<?>> logoutV1(HttpServletRequest request, @RequestBody LogoutFcmTokenRequest logoutFcmTokenRequest) {
		oauthLoginService.logout(request.getHeader(AUTHORIZATION), logoutFcmTokenRequest.fcmToken());
		return ResponseEntity.ok()
			.body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), null));
	}
}
