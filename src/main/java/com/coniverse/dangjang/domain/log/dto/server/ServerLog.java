package com.coniverse.dangjang.domain.log.dto.server;

/**
 * server log dto
 *
 * @author TEO
 * @since 1.0.0
 */
public record ServerLog(String group, String className, String methodName, long runningTime) {
}
