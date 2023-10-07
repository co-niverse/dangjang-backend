package com.coniverse.dangjang.domain.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coniverse.dangjang.domain.notification.entity.Notification;
import com.coniverse.dangjang.domain.notification.entity.NotificationId;

public interface NotificationRepository extends JpaRepository<Notification, NotificationId> {

}
