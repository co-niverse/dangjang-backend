package com.coniverse.dangjang.domain.log.dto.server;

public record ServerLog(String group, String className, String methodName, long runningTime) {
}
