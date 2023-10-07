package com.coniverse.dangjang.domain.notification;

import java.time.LocalDate;

import org.mapstruct.Mapper;

import com.coniverse.dangjang.domain.notification.entity.Notification;
import com.coniverse.dangjang.domain.user.entity.User;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

	Notification toEntity(User user, String fcmToken, LocalDate createdAt);
}
