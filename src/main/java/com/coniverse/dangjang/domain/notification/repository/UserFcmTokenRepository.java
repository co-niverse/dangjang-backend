package com.coniverse.dangjang.domain.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.coniverse.dangjang.domain.notification.entity.UserFcmToken;

import jakarta.transaction.Transactional;

/**
 * notification Repository
 *
 * @author EVE
 * @since 1.0.0
 */
public interface UserFcmTokenRepository extends JpaRepository<UserFcmToken, String> {

	/**
	 * FcmToken 제거
	 *
	 * @since 1.0.0
	 */
	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query(value = "DELETE UserFcmToken n  WHERE n.fcmToken = ?1")
	void deleteByFcmToken(String fcmToken);

}
