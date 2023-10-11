package com.coniverse.dangjang.domain.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coniverse.dangjang.domain.user.dto.response.DuplicateNicknameResponse;
import com.coniverse.dangjang.domain.user.dto.response.MypageResponse;
import com.coniverse.dangjang.domain.user.service.MypageService;
import com.coniverse.dangjang.domain.user.service.UserSignupService;
import com.coniverse.dangjang.domain.user.service.UserWithdrawalService;
import com.coniverse.dangjang.global.dto.SuccessSingleResponse;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;

/**
 * @author EVE
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Validated
public class UserController { // TODO 전체 수정 (위치: signup, 이름: duplicated-nickname, 바디없어도됨)
	private final UserSignupService userSignupService;
	private final MypageService mypageService;
	private final UserWithdrawalService userWithdrawalService;

	/**
	 * @param nickname 확인이 필요한 닉네임을 담아온다.
	 * @return 닉네임이 중복되지 않았으면 true, 중복된 닉네임이면 false를 담은 DuplicateNicknameResponse 객체를 반환한다.
	 * @since 1.0
	 */
	@GetMapping("/duplicateNickname")
	public ResponseEntity<SuccessSingleResponse<DuplicateNicknameResponse>> checkDuplicateNickname(
		@RequestParam @Pattern(regexp = "^[a-zA-Z]{1,8}$", message = "닉네임은 영어로만 이루어져있어야 합니다.") @NotBlank(message = "닉네임은 1~8글자 이내여야 합니다.") String nickname) {
		DuplicateNicknameResponse duplicateNicknameResponse = userSignupService.checkDuplicatedNickname(nickname);
		return ResponseEntity.ok().body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), duplicateNicknameResponse));
	}

	/**
	 * Mypage에 필요한 정보를 조회한다.
	 *
	 * @return MypageResponse 사용자 닉네임과 포인트
	 * @since 1.0.0
	 */
	@GetMapping("/mypage")
	public ResponseEntity<SuccessSingleResponse<MypageResponse>> getMyPage(@AuthenticationPrincipal User user) {
		MypageResponse response = mypageService.getMypage(user.getUsername());
		return ResponseEntity.ok().body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), response));
	}

	@DeleteMapping("/withdrawal")
	public ResponseEntity<?> withdraw(@AuthenticationPrincipal User user) {
		userWithdrawalService.withdraw(user.getUsername());
		return ResponseEntity.noContent().build();
	}
}
