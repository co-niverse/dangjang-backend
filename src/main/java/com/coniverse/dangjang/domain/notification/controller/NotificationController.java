package com.coniverse.dangjang.domain.notification.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coniverse.dangjang.domain.notification.dto.request.CheckNotificationIdRequest;
import com.coniverse.dangjang.domain.notification.dto.response.NotificationListResponse;
import com.coniverse.dangjang.domain.notification.service.NotificationService;
import com.coniverse.dangjang.global.dto.SuccessSingleResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Notification 관련 Controller
 *
 * @author EVE
 * @since 1.1.0
 */
@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {
	private final NotificationService notificationService;

	/**
	 * 알림 목록 조회
	 *
	 * @param user 사용자
	 * @return notificationList 확인 안된 알림 목록
	 * @since 1.0.0
	 */
	@GetMapping
	public ResponseEntity<SuccessSingleResponse<NotificationListResponse>> get(@AuthenticationPrincipal User user) {
		NotificationListResponse response = new NotificationListResponse(notificationService.getNotificationList(user.getUsername()));
		return ResponseEntity.ok(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), response));
	}

	/**
	 * 확인된 알림 변경 요청
	 * <p>
	 * 클라이언트에서 사용자가 확인한 알림Id 목록을 서버로 전달,
	 * 서버에서 알림 확인 여부를 변경한다.
	 *
	 * @param notificationIdList 알림Id 목록
	 * @since 1.0.0
	 */

	@PatchMapping
	public ResponseEntity<SuccessSingleResponse<?>> patch(@Valid @RequestBody CheckNotificationIdRequest notificationIdList) {
		notificationService.updateNotificationIsRead(notificationIdList);
		return ResponseEntity.ok(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), null));
	}

}
