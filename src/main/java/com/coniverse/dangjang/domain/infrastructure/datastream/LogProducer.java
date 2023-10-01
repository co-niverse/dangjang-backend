package com.coniverse.dangjang.domain.infrastructure.datastream;

import com.coniverse.dangjang.domain.log.dto.app.AppLog;
import com.coniverse.dangjang.domain.log.dto.server.ServerLog;

/**
 * log producer interface
 *
 * @author TEO
 * @since 1.0.0
 * @deprecated fluentbit으로 대체
 */
@Deprecated(since = "1.0.0")
public interface LogProducer {
	void sendMessage(AppLog message);

	void sendMessage(ServerLog message);
}
