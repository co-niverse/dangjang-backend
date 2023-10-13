package com.coniverse.dangjang.domain.log.dto.server;

import org.aspectj.lang.Signature;

/**
 * server log dto
 *
 * @author TEO
 * @since 1.0.0
 */
public record ServerLog(String group, String className, String methodName, long runningTime) {
	public ServerLog(String group, Signature signature, long runningTime) {
		this(group, signature.getDeclaringType().getSimpleName(), signature.getName(), runningTime);
	}
}
