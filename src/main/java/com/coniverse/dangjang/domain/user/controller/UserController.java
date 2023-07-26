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

/**
 * @author EVE
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
	private final UserService userService;

	/**
	 * @param nickname 확인이 필요한 닉네임을 담아온다.
	 * @return 닉네임이 중복되지 않았으면 true, 중복된 닉네임이면 false를 담은 DuplicateNicknameResponse 객체를 반환한다.
	 * @since 1.0
	 */
	@GetMapping("/duplicateNickname")
	public ResponseEntity<SuccessSingleResponse<DuplicateNicknameResponse>> checkDuplicateNickname(@RequestParam String nickname) {
		DuplicateNicknameResponse duplicateNicknameResponse = userService.checkDublicateNickname(nickname);
		return ResponseEntity.ok().body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), duplicateNicknameResponse));
	}
}