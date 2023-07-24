package com.coniverse.dangjang.domain.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coniverse.dangjang.domain.user.dto.DuplicateNicknameResponse;
import com.coniverse.dangjang.domain.user.service.UserService;
import com.coniverse.dangjang.global.dto.SuccessSingleResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
	private final UserService userService;

	@GetMapping("/duplicateNickname")
	public ResponseEntity<SuccessSingleResponse<DuplicateNicknameResponse>> checkDuplicateNickname(@RequestParam String nickname) {
		DuplicateNicknameResponse duplicateNicknameResponse = userService.checkDublicateNickname(nickname);
		return ResponseEntity.ok().body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), duplicateNicknameResponse));
	}
}
