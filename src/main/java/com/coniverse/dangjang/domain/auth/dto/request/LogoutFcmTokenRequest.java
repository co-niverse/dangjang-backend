package com.coniverse.dangjang.domain.auth.dto.request;

/**
 * fcmToken을 받아오는 DTO
 *
 * @author EVE
 * @since 1.3.0
 */
public record LogoutFcmTokenRequest(String fcmToken) {
}
