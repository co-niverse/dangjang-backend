package com.coniverse.dangjang.domain.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coniverse.dangjang.domain.notification.entity.NotificationType;

/**
 * NotificationType Repository
 *
 * @author EVE
 * @since 1.1.0
 */

public interface NotificationTypeRepository extends JpaRepository<NotificationType, String> {
}
