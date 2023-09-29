package com.coniverse.dangjang.domain.log.dto.request;

import java.util.Map;

/**
 * client log request
 *
 * @author TEO
 * @since 1.0.0
 */
public record ClientLogRequest(String eventLogName, String screenName, int logVersion, String sessionId, Map<String, String> logData) {
}
