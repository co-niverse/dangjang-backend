package com.coniverse.dangjang.domain.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coniverse.dangjang.domain.auth.dto.response.LoginResponse;
import com.coniverse.dangjang.domain.auth.service.OauthLoginService;
import com.coniverse.dangjang.domain.user.dto.request.SignUpRequest;
import com.coniverse.dangjang.domain.user.service.UserSignupService;
import com.coniverse.dangjang.global.dto.SuccessSingleResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * @author EVE
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/signup")
public class SignupController {
	private final UserSignupService userSignupService;
	private final OauthLoginService oauthLoginService;

	/**
	 * @param params 회원가입에 필요한 정보를 담아온다.
	 * @return 회원가입 후 로그인을 시도 , ResponseEntity 로그인을 성공하면, JWT TOKEN과 사용자 정보(nickname, authID)를 전달한다.
	 * @since 1.0
	 */
	@PostMapping
	public ResponseEntity<SuccessSingleResponse<LoginResponse>> signUp(@Valid @RequestBody SignUpRequest params) {
		LoginResponse loginResponse = userSignupService.signUp(params);
		String accessToken = oauthLoginService.getAuthToken(loginResponse.nickname());

		return ResponseEntity.ok()
			.header("AccessToken", accessToken)
			.body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), loginResponse));
	}
}
