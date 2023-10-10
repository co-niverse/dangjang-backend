package com.coniverse.dangjang.domain.notification.dto.response;

import java.util.List;

/**
 * Notifiacation List Response
 *
 * @author EVE
 * @since 1.1.0
 */
public record NotificationListResponse(List<NotificationResponse> notificationResponseList) {
}
