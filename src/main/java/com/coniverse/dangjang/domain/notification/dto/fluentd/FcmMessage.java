package com.coniverse.dangjang.domain.notification.dto.fluentd;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * FcmMessage
 *
 * @author EVE
 * @since 1.1.0
 */
public record FcmMessage(@JsonProperty("registration_token") String registrationToken, String title, String body) {
}
