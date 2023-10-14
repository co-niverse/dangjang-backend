package com.coniverse.dangjang.domain.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coniverse.dangjang.domain.notification.entity.NotificationType;

/**
 * NotificationType Repository
 *
 * @author EVE
 * @since 1.1.0
 */
@Repository
public interface NotificationTypeRepository extends JpaRepository<NotificationType, String> {
}
