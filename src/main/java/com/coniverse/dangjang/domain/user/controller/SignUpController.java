package com.coniverse.dangjang.domain.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coniverse.dangjang.domain.auth.dto.Response.LoginResponse;
import com.coniverse.dangjang.domain.user.dto.SignUpRequest;
import com.coniverse.dangjang.domain.user.service.UserService;
import com.coniverse.dangjang.global.dto.SuccessSingleResponse;

import lombok.RequiredArgsConstructor;

/**
 * @author EVE
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SignUpController {
	private final UserService userService;

	/**
	 * @param params 회원가입에 필요한 정보를 담아온다.
	 * @return 회원가입 후 로그인을 시도 , ResponseEntity 로그인을 성공하면, JWT TOKEN과 사용자 정보(nickname, authID)를 전달한다.
	 * @since 1.0
	 */
	@PostMapping("/signUp")
	public ResponseEntity<SuccessSingleResponse<LoginResponse>> signUp(@RequestBody SignUpRequest params) {
		LoginResponse loginResponse = userService.signUp(params);
		return ResponseEntity.ok().body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), loginResponse));
	}
}
