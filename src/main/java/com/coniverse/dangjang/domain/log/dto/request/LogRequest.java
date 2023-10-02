package com.coniverse.dangjang.domain.log.dto.request;

import java.util.Map;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

/**
 * client log request dto
 *
 * @author TEO
 * @since 1.0.0
 */
public record LogRequest(@NotBlank String eventLogName, @NotBlank String screenName, @Positive int logVersion, @NotBlank String sessionId,
						 Map<String, Object> logData) {
}
