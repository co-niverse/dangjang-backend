package com.coniverse.dangjang.domain.infrastructure.datastream.kafka;

import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;

import com.coniverse.dangjang.domain.infrastructure.datastream.LogProducer;
import com.coniverse.dangjang.domain.infrastructure.datastream.enums.Topic;
import com.coniverse.dangjang.domain.log.dto.app.AppLog;
import com.coniverse.dangjang.domain.log.dto.server.ServerLog;

import lombok.RequiredArgsConstructor;

/**
 * kafka producer
 *
 * @author TEO
 * @since 1.0.0
 * @deprecated fluentbit으로 대체
 */
// @Service
@RequiredArgsConstructor
@Profile({"local"})
@Deprecated(since = "1.0.0")
public class KafkaProducer implements LogProducer {
	private final KafkaTemplate<Integer, AppLog> kafkaClientLogTemplate;
	private final KafkaTemplate<Integer, ServerLog> kafkaServerLogTemplate;

	@Override
	public void sendMessage(AppLog message) {
		kafkaClientLogTemplate.send("local" + Topic.CLIENT_LOG.getName(), message);
	}

	@Override
	public void sendMessage(ServerLog message) {
		kafkaServerLogTemplate.send("local" + Topic.SERVER_LOG.getName(), message);
	}
}
