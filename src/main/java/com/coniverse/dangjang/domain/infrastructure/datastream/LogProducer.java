package com.coniverse.dangjang.domain.infrastructure.datastream;

import com.coniverse.dangjang.domain.log.dto.request.ClientLogRequest;
import com.coniverse.dangjang.global.aop.log.ServerLog;

/**
 * log producer interface
 *
 * @author TEO
 * @since 1.0.0
 * @deprecated fluentbit으로 대체
 */
public interface LogProducer {
	void sendMessage(ClientLogRequest<?> message);

	void sendMessage(ServerLog message);
}
