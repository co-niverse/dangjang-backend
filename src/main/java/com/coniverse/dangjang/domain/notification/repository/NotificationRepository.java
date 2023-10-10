package com.coniverse.dangjang.domain.notification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.coniverse.dangjang.domain.notification.entity.Notification;

/**
 * Notification Repository
 *
 * @author EVE
 * @since 1.0.0
 */
public interface NotificationRepository extends JpaRepository<Notification, Long> {

	/**
	 * 사용자의 읽지 않은 알림 목록을 조회한다.
	 *
	 * @param oauthId 사용자 아이디
	 * @return List<Notification> 알림 목록
	 * @since 1.0.0
	 */
	@Query("SELECT n FROM Notification n WHERE n.user.oauthId = ?1 AND n.isRead = false")
	List<Notification> findAllByOauthIdAndRead(String oauthId);

	/**
	 * 읽지 않은 알림이 있는지 조회한다
	 *
	 * @param oauthId 사용자Id
	 * @return Boolean 읽지 않은 알림이 있으면 true, 없으면 false 반환
	 * @since 1.0.0
	 */
	@Query("SELECT CASE WHEN COUNT(n) > 0 THEN true ELSE false END FROM Notification n WHERE n.user.oauthId=?1 AND n.isRead = false")
	Boolean isExistsNotReadNotification(String oauthId);
}
