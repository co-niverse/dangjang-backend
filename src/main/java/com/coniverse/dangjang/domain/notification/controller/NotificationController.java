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

import given.apiversion.core.annotation.ApiVersion;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Notification 관련 Controller
 *
 * @author EVE
 * @since 1.1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {
	private final NotificationService notificationService;

	/**
	 * 알림 목록 조회
	 *
	 * @param user 사용자
	 * @return notificationList 확인 안된 알림 목록
	 * @since 1.0.0
	 * @deprecated 1.4.0
	 */
	@Deprecated(since = "1.4.0")
	@GetMapping
	public ResponseEntity<SuccessSingleResponse<NotificationListResponse>> get(@AuthenticationPrincipal User user) {
		NotificationListResponse response = new NotificationListResponse(notificationService.getNotificationList(user.getUsername()));
		return ResponseEntity.ok(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), response));
	}

	/**
	 * 미확인 알림 목록 조회
	 *
	 * @param user 사용자 정보
	 * @return notificationList 확인하지 않은 알림 목록
	 * @since 1.4.0
	 */
	@ApiVersion("1")
	@GetMapping
	public ResponseEntity<SuccessSingleResponse<NotificationListResponse>> getUnreadNotificationsV1(@AuthenticationPrincipal User user) {
		NotificationListResponse response = new NotificationListResponse(notificationService.getNotificationList(user.getUsername())); // TODO 객체 생성 이동
		return ResponseEntity.ok()
			.body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), response));
	}

	/**
	 * 확인된 알림 변경 요청
	 * <p>
	 * 클라이언트에서 사용자가 확인한 알림Id 목록을 서버로 전달,
	 * 서버에서 알림 확인 여부를 변경한다.
	 *
	 * @param notificationIdList 알림Id 목록
	 * @since 1.0.0
	 * @deprecated 1.4.0
	 */
	@Deprecated(since = "1.4.0")
	@PatchMapping
	public ResponseEntity<SuccessSingleResponse<?>> patch(@Valid @RequestBody CheckNotificationIdRequest notificationIdList) {
		notificationService.updateNotificationIsRead(notificationIdList);
		return ResponseEntity.ok(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), null));
	}

	/**
	 * 확인된 푸쉬 알림 변경
	 * <p>
	 * 클라이언트에서 사용자가 확인한 알림 id 목록을 서버로 전달하고 서버에서 알림 확인 여부를 변경한다.
	 *
	 * @param notificationIds 푸쉬 알림 id 목록
	 * @since 1.4.0
	 */
	@ApiVersion("1")
	@PatchMapping
	public ResponseEntity<SuccessSingleResponse<?>> patchNotificationsAsReadV1(@Valid @RequestBody CheckNotificationIdRequest notificationIds) {
		notificationService.updateNotificationIsRead(notificationIds);
		return ResponseEntity.ok()
			.body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), null));
	}
}
