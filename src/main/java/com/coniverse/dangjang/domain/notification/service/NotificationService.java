package com.coniverse.dangjang.domain.notification.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.notification.NotificationMapper;
import com.coniverse.dangjang.domain.notification.entity.Notification;
import com.coniverse.dangjang.domain.notification.repository.NotificationRepository;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.exception.NonExistentUserException;
import com.coniverse.dangjang.domain.user.service.UserSearchService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * 알림 Service
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
	 * @param token    fcmToken
	 * @param nickname 사용자 닉네임
	 * @since 1.0.0
	 */
	public void saveFcmToken(String token, String nickname) {
		User user = userSearchService.findNickname(nickname).orElseThrow(NonExistentUserException::new);
		//TODO : Mapper 오류
		Notification notification = Notification.builder()
			.user(user)
			.fcmToken(token)
			.createdAt(LocalDate.now())
			.build();
		notificationRepository.save(notification);
	}
}
