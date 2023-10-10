package com.coniverse.dangjang.domain.notification.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.notification.dto.request.CheckNotificationIdRequest;
import com.coniverse.dangjang.domain.notification.dto.response.NotificationResponse;
import com.coniverse.dangjang.domain.notification.entity.Notification;
import com.coniverse.dangjang.domain.notification.exception.InvalidFcmTokenException;
import com.coniverse.dangjang.domain.notification.mapper.NotificationMapper;
import com.coniverse.dangjang.domain.notification.repository.NotificationRepository;
import com.coniverse.dangjang.domain.notification.repository.UserFcmTokenRepository;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.service.UserSearchService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * Notification 관련 Service
 *
 * @author EVE
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class NotificationService {
	private final UserFcmTokenRepository userFcmTokenRepository;
	private final NotificationMapper notificationMapper;
	private final UserSearchService userSearchService;
	private final NotificationRepository notificationRepository;

	/**
	 * 유저의 알림 목록을 조회한다
	 *
	 * @param oauthId 사용자 oauthId
	 * @return List<NotificationResponse> 알림 목록
	 * @since 1.0.0
	 */
	public List<NotificationResponse> getNotificationList(String oauthId) {
		return notificationRepository.findAllByOauthIdAndRead(oauthId).stream()
			.map(notificationMapper::toResponse).collect(Collectors.toList());
	}

	/**
	 * 알림 확인 여부(isRead)를 true로 변경한다
	 *
	 * @param notificationIdList 알림Id 목록
	 * @since 1.0.0
	 */
	public void updateNotificationIsRead(CheckNotificationIdRequest notificationIdList) {
		//Todo : update 로직 수정 필요
		notificationIdList.notificationIdList().forEach(notificationId -> {
			Optional<Notification> notification = notificationRepository.findById(notificationId);
			if (notification.isPresent() && !notification.get().isRead()) {
				notification.get().setRead();
				notificationRepository.save(notification.get());
			}
		});
	}

	/**
	 * 확인 안한 알림이 있는지 조회한다.
	 *
	 * @param oauthId 사용자 oauthId
	 * @return Boolean 읽지 않은 알림이 있으면 true, 없으면 false 반환
	 * @since 1.0.0
	 */
	public Boolean isExistsNotReadNotification(String oauthId) {
		return notificationRepository.isExistsNotReadNotification(oauthId);
	}

	/**
	 * fcmToken 저장
	 *
	 * @param fcmToken fcmToken
	 * @param oauthId  사용자 아이디
	 * @since 1.0.0
	 */
	public void saveFcmToken(String fcmToken, String oauthId) {
		User user = userSearchService.findUserByOauthId(oauthId);
		userFcmTokenRepository.save(notificationMapper.toEntity(user, fcmToken, LocalDate.now()));
	}

	/**
	 * fcmToken 제거
	 *
	 * @param fcmToken fcmToken
	 * @throws InvalidFcmTokenException fcmToken이 존재하지 않을 경우 발생하는 예외
	 * @since 1.0.0
	 */
	public void deleteFcmToken(String fcmToken) {
		userFcmTokenRepository.findById(fcmToken).orElseThrow(
			() -> new InvalidFcmTokenException()
		);
		userFcmTokenRepository.deleteById(fcmToken);
	}
}
