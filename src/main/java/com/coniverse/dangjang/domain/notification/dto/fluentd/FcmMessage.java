package com.coniverse.dangjang.domain.notification.dto.fluentd;

/**
 * FcmMessage
 *
 * @author EVE
 * @since 1.1.0
 */
public record FcmMessage(String registrationTokens, String title, String body) {
}
