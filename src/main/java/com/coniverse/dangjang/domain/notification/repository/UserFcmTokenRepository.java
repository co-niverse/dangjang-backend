package com.coniverse.dangjang.domain.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coniverse.dangjang.domain.notification.entity.UserFcmToken;

/**
 * notification Repository
 *
 * @author EVE
 * @since 1.0.0
 */
public interface UserFcmTokenRepository extends JpaRepository<UserFcmToken, String> {
	
}
