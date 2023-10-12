package com.coniverse.dangjang.domain.notification.dto.fluentd;

import java.util.List;

/**
 * FcmMessage
 *
 * @author EVE
 * @since 1.1.0
 */
public record FcmMessage(List<String> registrationTokens, String title, String body) {
}
