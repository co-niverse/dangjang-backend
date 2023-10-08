package com.coniverse.dangjang.domain.notification.dto.response;

/**
 * notification response dto
 *
 * @param notificationId 알림Id
 * @param title          알림 제목
 * @param content        알림 내용
 * @param type           알림 타입
 * @param createdAt      알림 생성일
 * @author EVE
 * @since 1.0.0
 */
public record NotificationResponse(long notificationId, String title, String content, String type, String createdAt) {

}
