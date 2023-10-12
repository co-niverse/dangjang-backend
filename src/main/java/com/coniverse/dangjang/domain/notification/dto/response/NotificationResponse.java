package com.coniverse.dangjang.domain.notification.dto.response;

import java.time.LocalDate;

/**
 * notification response dto
 *
 * @author EVE
 * @since 1.1.0
 */
public record NotificationResponse(long notificationId, String title, String content, String type, LocalDate createdAt) {

}
