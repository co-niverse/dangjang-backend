package com.coniverse.dangjang.domain.notification.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.notification.NotificationMapper;
import com.coniverse.dangjang.domain.notification.entity.Notification;
import com.coniverse.dangjang.domain.notification.repository.NotificationRepository;
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
	private final NotificationRepository notificationRepository;
	private final NotificationMapper notificationMapper;
	private final UserSearchService userSearchService;

	/**
	 * fcmToken 저장
	 *
	 * @param fcmToken fcmToken
	 * @param oauthId  사용자 아이디
	 * @since 1.0.0
	 */
	public void saveFcmToken(String oauthId, String fcmToken) {
		User user = userSearchService.findUserByOauthId(oauthId);
		//TODO : Mapper 오류
		Notification notification = Notification.builder()
			.user(user)
			.fcmToken(fcmToken)
			.createdAt(LocalDate.now())
			.build();
		notificationRepository.save(notification);
	}

	/**
	 * fcmToken 제거
	 *
	 * @param fcmToken fcmToken
	 * @since 1.0.0
	 */
	public void deleteFcmToken(String fcmToken) {
		notificationRepository.deleteByFcmToken(fcmToken);
	}
}
