package com.coniverse.dangjang.domain.log.dto.app;

import java.util.Map;

/**
 * app log
 *
 * @author TEO
 * @since 1.0.0
 */
public record AppLog(String eventLogName, String screenName, int logVersion, String sessionId, Map<String, String> logData) {
}
