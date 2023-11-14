package com.coniverse.dangjang.domain.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coniverse.dangjang.domain.notification.service.NotificationService;
import com.coniverse.dangjang.domain.user.dto.request.PostFcmTokenRequest;
import com.coniverse.dangjang.domain.user.dto.response.DuplicateNicknameResponse;
import com.coniverse.dangjang.domain.user.dto.response.MypageResponse;
import com.coniverse.dangjang.domain.user.service.MypageService;
import com.coniverse.dangjang.domain.user.service.UserSignupService;
import com.coniverse.dangjang.domain.user.service.UserWithdrawalService;
import com.coniverse.dangjang.global.dto.SuccessSingleResponse;

import given.apiversion.core.annotation.ApiVersion;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;

/**
 * @author EVE
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/user")
public class UserController {
	private static final String NICKNAME_PATTERN = "^[가-힣ㄱ-ㅎㅏ-ㅣa-zA-Z0-9]{1,8}$";
	private final UserSignupService userSignupService;
	private final MypageService mypageService;
	private final UserWithdrawalService userWithdrawalService;
	private final NotificationService notificationService;

	/**
	 * @param nickname 확인이 필요한 닉네임을 담아온다.
	 * @return 닉네임이 중복되지 않았으면 true, 중복된 닉네임이면 false를 담은 DuplicateNicknameResponse 객체를 반환한다.
	 * @since 1.0
	 * @deprecated 1.6.0
	 */
	@Deprecated(since = "1.6.0")
	@GetMapping("/duplicateNickname")
	public ResponseEntity<SuccessSingleResponse<DuplicateNicknameResponse>> checkDuplicateNickname(
		@RequestParam @Pattern(regexp = "^[가-힣ㄱ-ㅎㅏ-ㅣa-zA-Z0-9]{1,8}$", message = "닉네임은 영어,한글,숫자 1~8글자 이내로 이루어져있어야 합니다.") @NotBlank(message = "닉네임은 1~8글자 이내여야 합니다.") String nickname) {
		DuplicateNicknameResponse duplicateNicknameResponse = userSignupService.checkDuplicatedNickname(nickname);
		return ResponseEntity.ok().body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), duplicateNicknameResponse));
	}

	/**
	 * 닉네임 중복 여부 확인
	 *
	 * @param nickname 확인이 필요한 닉네임
	 * @return 닉네임이 중복되지 않았으면 true, 중복된 닉네임이면 false를 담은 DuplicateNicknameResponse 객체를 반환한다.
	 * @since 1.6.0
	 */
	@ApiVersion("1")
	@GetMapping("/duplicateNickname") // TODO 위치: signup, 이름: duplicated-nickname, 바디없어도됨
	public ResponseEntity<SuccessSingleResponse<DuplicateNicknameResponse>> verifyDuplicatedNicknameV1(
		@RequestParam @Pattern(regexp = NICKNAME_PATTERN, message = "닉네임은 영어, 한글, 숫자 1 ~ 8글자 이내로 이루어져있어야 합니다.") @NotBlank(message = "닉네임은 1~8글자 이내여야 합니다.") String nickname) {
		DuplicateNicknameResponse duplicateNicknameResponse = userSignupService.checkDuplicatedNickname(nickname);
		return ResponseEntity.ok()
			.body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), duplicateNicknameResponse));
	}

	/**
	 * Mypage에 필요한 정보를 조회한다.
	 *
	 * @return MypageResponse 사용자 닉네임과 포인트
	 * @since 1.0.0
	 * @deprecated 1.6.0
	 */
	@Deprecated(since = "1.6.0")
	@GetMapping("/mypage")
	public ResponseEntity<SuccessSingleResponse<MypageResponse>> getMyPage(@AuthenticationPrincipal User user) {
		MypageResponse response = mypageService.getMypage(user.getUsername());
		return ResponseEntity.ok().body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), response));
	}

	/**
	 * Mypage에 필요한 사용자 정보 조회
	 *
	 * @param user 사용자 정보
	 * @return 닉네임, 보유 포인트
	 * @since 1.6.0
	 */
	@ApiVersion("1")
	@GetMapping("/mypage")
	public ResponseEntity<SuccessSingleResponse<MypageResponse>> getMyPageV1(@AuthenticationPrincipal User user) {
		MypageResponse response = mypageService.getMypage(user.getUsername());
		return ResponseEntity.ok()
			.body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), response));
	}

	/**
	 * 회원 탈퇴
	 *
	 * @param user 사용자 정보
	 * @return ResponseEntity
	 * @since 1.0.0
	 * @deprecated 1.6.0
	 */
	@Deprecated(since = "1.6.0")
	@DeleteMapping("/withdrawal")
	public ResponseEntity<?> withdraw(@AuthenticationPrincipal User user) {
		userWithdrawalService.withdraw(user.getUsername());
		return ResponseEntity.noContent().build();
	}

	/**
	 * 회원 탈퇴
	 *
	 * @param user 사용자 정보
	 * @return ResponseEntity
	 * @since 1.6.0
	 */
	@ApiVersion("1")
	@DeleteMapping("/withdrawal")
	public ResponseEntity<?> withdrawV1(@AuthenticationPrincipal User user) {
		userWithdrawalService.withdraw(user.getUsername());
		return ResponseEntity.noContent().build();
	}

	/**
	 * fcmToken 저장 및 업데이트
	 *
	 * @param user    사용자 정보
	 * @param request fcmToken
	 * @return MyPageResponse 사용자 닉네임과 포인트
	 * @since 1.0.0
	 * @deprecated 1.6.0
	 */
	@Deprecated(since = "1.6.0")
	@PostMapping("/fcmToken")
	public ResponseEntity<SuccessSingleResponse<?>> postFcmToken(@AuthenticationPrincipal User user, @Valid @RequestBody PostFcmTokenRequest request) {
		notificationService.saveOrUpdateFcmToken(request, user.getUsername());
		return ResponseEntity.ok().body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), null));
	}

	/**
	 * fcm token 등록
	 *
	 * @param user    사용자 정보
	 * @param request fcm token 정보
	 * @since 1.6.0
	 */
	@ApiVersion("1")
	@PostMapping("/fcmToken")
	public ResponseEntity<SuccessSingleResponse<?>> postFcmTokenV1(@AuthenticationPrincipal User user, @Valid @RequestBody PostFcmTokenRequest request) {
		notificationService.saveOrUpdateFcmToken(request, user.getUsername());
		return ResponseEntity.ok()
			.body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), null));
	}
}
