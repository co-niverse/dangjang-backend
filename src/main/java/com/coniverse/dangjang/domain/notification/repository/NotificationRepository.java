package com.coniverse.dangjang.domain.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.coniverse.dangjang.domain.notification.entity.Notification;
import com.coniverse.dangjang.domain.notification.entity.NotificationId;

import jakarta.transaction.Transactional;

/**
 * notification Repository
 *
 * @author EVE
 * @since 1.0.0
 */
public interface NotificationRepository extends JpaRepository<Notification, NotificationId> {

	/**
	 * FcmToken 제거
	 *
	 * @since 1.0.0
	 */
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "DELETE Notification n  WHERE n.notificationId.fcmToken = ?1")
	void deleteByFcmToken(String fcmToken);

}
