package com.coniverse.dangjang.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;

/**
 * fcmToken 등록 및 업데이트 요청 request
 *
 * @author EVE
 * @since 1.3.0
 */
public record PostFcmTokenRequest(@NotBlank(message = "FCM Token은 필수로 필요합니다") String fcmToken, @NotBlank(message = "Device ID는 필수로 필요합니다") String deviceId) {

}
