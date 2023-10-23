package com.coniverse.dangjang.domain.log.dto.app;

import java.util.Map;

import com.coniverse.dangjang.domain.log.enums.Event;

/**
 * app log dto
 *
 * @author TEO
 * @since 1.0.0
 */
public record AppLog(Event event, String screenName, int logVersion, String appVersion, String sessionId, Map<String, Object> logData) {
}
