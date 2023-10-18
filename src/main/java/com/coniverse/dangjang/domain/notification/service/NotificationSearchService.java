package com.coniverse.dangjang.domain.notification.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.notification.entity.NotificationType;
import com.coniverse.dangjang.domain.notification.entity.UserFcmToken;
import com.coniverse.dangjang.domain.notification.exception.InvalidNotificationTypeException;
import com.coniverse.dangjang.domain.notification.repository.NotificationTypeRepository;
import com.coniverse.dangjang.domain.notification.repository.UserFcmTokenRepository;

import lombok.RequiredArgsConstructor;

/**
 * notification search service
 *
 * @author EVE
 * @since 1.1.0
 */

@Service
@RequiredArgsConstructor
public class NotificationSearchService {
	private final UserFcmTokenRepository userFcmTokenRepository;
	private final NotificationTypeRepository notificationTypeRepository;

	/**
	 * 접속하지 않은 유저의 fcmToken을 조회한다.
	 *
	 * @param date 조회 날짜
	 * @return List<UserFcmToken> 유저의 fcmToken
	 * @since 1.1.0
	 */
	public List<UserFcmToken> findNotAccessUserFcmToken(LocalDate date) {
		return userFcmTokenRepository.findNotAccessUserFcmToken(date);
	}

	/**
	 * NotificationType을 조회한다
	 *
	 * @param type 조회할 NotificationType
	 * @return NotificationType
	 * @since 1.1.0
	 */
	public NotificationType findNotificationType(String type) {
		return notificationTypeRepository.findById(type).orElseThrow(InvalidNotificationTypeException::new);
	}
}
