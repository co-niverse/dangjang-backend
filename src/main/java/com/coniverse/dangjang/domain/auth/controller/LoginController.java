package com.coniverse.dangjang.domain.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coniverse.dangjang.domain.auth.dto.Response.LoginResponse;
import com.coniverse.dangjang.domain.auth.dto.request.KakaoLoginParams;
import com.coniverse.dangjang.domain.auth.dto.request.NaverLoginParams;
import com.coniverse.dangjang.domain.auth.service.OauthLoginService;
import com.coniverse.dangjang.global.dto.SuccessSingleResponse;
import com.coniverse.dangjang.global.exception.NonExistentUserException;

import lombok.RequiredArgsConstructor;

/**
 * @author Eve
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class LoginController {
	private final OauthLoginService oauthLoginService;

	/**
	 * @param params 카카오 accessToken을 받아온다.
	 * @return ResponseEntity 로그인을 성공하면, JWT TOKEN과 사용자 정보(nickname, authID)를 전달한다.
	 * @throws NonExistentUserException 회원가입된 유저가 아닐때 발생하는 오류 (oAuthLoginService.login에서 throw)
	 * @since 1.0
	 */
	@PostMapping("/kakao")
	public ResponseEntity<SuccessSingleResponse<LoginResponse>> loginKakao(@RequestBody KakaoLoginParams params) {
		LoginResponse loginResponse = oauthLoginService.login(params);
		return ResponseEntity.ok().body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), loginResponse));
	}

	/**
	 * @param params 네이버 accessToken을 받아온다.
	 * @return ResponseEntity 로그인을 성공하면, JWT TOKEN과 사용자 정보(nickname, authID)를 전달한다.
	 * @throws NonExistentUserException 회원가입된 유저가 아닐때 발생하는 오류
	 * @since 1.0
	 */
	@PostMapping("/naver")
	public ResponseEntity<SuccessSingleResponse<LoginResponse>> loginNaver(@RequestBody NaverLoginParams params) {
		LoginResponse loginResponse = oauthLoginService.login(params);
		return ResponseEntity.ok().body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), loginResponse));
	}
}

