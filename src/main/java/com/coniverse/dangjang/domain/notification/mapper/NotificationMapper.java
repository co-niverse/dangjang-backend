package com.coniverse.dangjang.domain.notification.mapper;

import java.time.LocalDate;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.coniverse.dangjang.domain.notification.dto.response.NotificationResponse;
import com.coniverse.dangjang.domain.notification.entity.Notification;
import com.coniverse.dangjang.domain.notification.entity.UserFcmToken;
import com.coniverse.dangjang.domain.user.entity.User;

/**
 * Notification 관련 Mapper
 *
 * @author EVE
 * @since 1.0.0
 */
@Mapper(componentModel = "spring")
public interface NotificationMapper {
	/**
	 * Notification Entity를 Response로 변환
	 *
	 * @param notification notification Entity
	 * @return NotificationResponse
	 * @since 1.0.0
	 */
	NotificationResponse toResponse(Notification notification);

	/**
	 * UserFcmToken Entity 생성
	 *
	 * @param user      사용자
	 * @param fcmToken  사용자 fcmToken
	 * @param createdAt fcmToken 저장 날짜
	 * @return UserFcmToken 사용자 fcmToken Entity
	 * @since 1.0.0
	 */
	@Mappings({
		@Mapping(target = "oauthId", source = "user.oauthId"),
		@Mapping(target = "createdAt", source = "createdAt")
	})
	UserFcmToken toEntity(User user, String fcmToken, LocalDate createdAt);
}
