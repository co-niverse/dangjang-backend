package com.coniverse.dangjang.domain.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coniverse.dangjang.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SignUpController {
	private final UserService userService;

	// @PostMapping("/signUp")
	// public ResponseEntity<SuccessSingleResponse<UserId>> signUp(@RequestBody SignUpRequest params) {
	// 	userService.signUp(params);
	// 	UserId userId = new UserId();
	// 	return ResponseEntity.ok().body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), userId));
	// }
}
