package com.coniverse.dangjang.domain.notification.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;

/**
 * 확인된 notificationId request dto
 *
 * @author EVE
 * @since 1.1.0
 */
public record CheckNotificationIdRequest(@NotEmpty(message = "NotificationIdList가 비어있습니다.") List<Long> notificationIdList) {
}
