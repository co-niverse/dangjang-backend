package com.coniverse.dangjang.global.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.coniverse.dangjang.domain.infrastructure.datastream.enums.Topic;
import com.coniverse.dangjang.domain.log.dto.request.ClientLogRequest;
import com.coniverse.dangjang.global.aop.log.ServerLog;

/**
 * Kafka Configuration
 *
 * @author TEO
 * @since 1.0.0
 * @deprecated fluentbit으로 대체
 */
// @Configuration
@Profile({"local"})
@Deprecated(since = "1.0.0")
public class KafkaConfig {
	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapAddress;

	/**
	 * client log producer factory
	 *
	 * @since 1.0.0
	 */
	@Bean
	public ProducerFactory<Integer, ClientLogRequest> producerFactoryClientLog() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return new DefaultKafkaProducerFactory<>(configProps);
	}

	/**
	 * kafka template for client log
	 *
	 * @since 1.0.0
	 */
	@Bean
	public KafkaTemplate<Integer, ClientLogRequest> kafkaTemplateClientLog() {
		return new KafkaTemplate<>(producerFactoryClientLog());
	}

	/**
	 * server log producer factory
	 *
	 * @since 1.0.0
	 */
	@Bean
	public ProducerFactory<Integer, ServerLog> producerFactoryServerLog() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return new DefaultKafkaProducerFactory<>(configProps);
	}

	/**
	 * kafka template for server log
	 *
	 * @since 1.0.0
	 */
	@Bean
	public KafkaTemplate<Integer, ServerLog> kafkaTemplateServerLog() {
		return new KafkaTemplate<>(producerFactoryServerLog());
	}

	/**
	 * kafka admin
	 *
	 * @since 1.0.0
	 */
	@Bean
	public KafkaAdmin admin() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		return new KafkaAdmin(configs);
	}

	/**
	 * client log topic
	 *
	 * @since 1.0.0
	 */
	@Bean
	public NewTopic topicClientLog() {
		return TopicBuilder
			.name("local" + Topic.CLIENT_LOG.getName())
			.partitions(3)
			.replicas(3)
			.build();
	}

	/**
	 * server log topic
	 *
	 * @since 1.0.0
	 */
	@Bean
	public NewTopic topicServerLog() {
		return TopicBuilder
			.name("local" + Topic.SERVER_LOG.getName())
			.partitions(3)
			.replicas(3)
			.build();
	}
}
