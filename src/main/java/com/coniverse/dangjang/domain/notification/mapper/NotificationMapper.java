package com.coniverse.dangjang.domain.notification.mapper;

import java.time.LocalDate;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.coniverse.dangjang.domain.notification.dto.response.NotificationResponse;
import com.coniverse.dangjang.domain.notification.entity.Notification;
import com.coniverse.dangjang.domain.notification.entity.NotificationType;
import com.coniverse.dangjang.domain.notification.entity.UserFcmToken;
import com.coniverse.dangjang.domain.user.entity.User;

/**
 * Notification 관련 Mapper
 *
 * @author EVE
 * @since 1.1.0
 */
@Mapper(componentModel = "spring")
public interface NotificationMapper {
	/**
	 * Notification Entity를 Response로 변환
	 *
	 * @param notification notification Entity
	 * @return NotificationResponse
	 * @since 1.1.0
	 */
	@Mappings({
		@Mapping(target = "type", source = "notification.notificationType.type"),
	})
	NotificationResponse toResponse(Notification notification);

	/**
	 * UserFcmToken Entity 생성
	 *
	 * @param user     사용자
	 * @param fcmToken 사용자 fcmToken
	 * @return UserFcmToken 사용자 fcmToken Entity
	 * @since 1.1.0
	 */
	@Mappings({
		@Mapping(target = "user", source = "user"),
		@Mapping(target = "deviceId", source = "deviceId")
	})
	UserFcmToken toEntity(User user, String fcmToken, String deviceId);

	/**
	 * Notification Entity 생성
	 *
	 * @param user             사용자
	 * @param title            사용자 title
	 * @param content          사용자 content
	 * @param createdAt        fcmToken 저장 날짜
	 * @param notificationType notificationType
	 * @return Notification Entity
	 * @since 1.1.0
	 */
	@Mappings({
		@Mapping(target = "user", source = "user"),
		@Mapping(target = "createdAt", source = "createdAt")
	})
	Notification toEntity(User user, String title, String content, LocalDate createdAt, NotificationType notificationType);

}
