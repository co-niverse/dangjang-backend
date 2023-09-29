package com.coniverse.dangjang.domain.infrastructure.datastream.kinesis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;

import com.coniverse.dangjang.domain.infrastructure.datastream.LogProducer;
import com.coniverse.dangjang.domain.infrastructure.datastream.enums.Topic;
import com.coniverse.dangjang.domain.log.dto.request.ClientLogRequest;
import com.coniverse.dangjang.global.aop.log.ServerLog;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.kinesis.KinesisAsyncClient;
import software.amazon.awssdk.services.kinesis.model.PutRecordRequest;

/**
 * kinesis producer
 *
 * @author TEO
 * @since 1.0.0
 * @deprecated fluentbit으로 대체
 */
// @Service
@RequiredArgsConstructor
@Profile({"dev", "prod"})
@Deprecated(since = "1.0.0")
public class KinesisProducer implements LogProducer {
	private final KinesisAsyncClient kinesisAsyncClient;
	@Value("${spring.profiles.active}")
	private String env;

	@Override
	public void sendMessage(ClientLogRequest message) {
		PutRecordRequest request = PutRecordRequest.builder()
			.partitionKey("partition key")
			.streamName(env + Topic.CLIENT_LOG.getName())
			.data(SdkBytes.fromByteArray(message.toString().getBytes()))
			.build();

		kinesisAsyncClient.putRecord(request);
	}

	@Override
	public void sendMessage(ServerLog message) {
		PutRecordRequest request = PutRecordRequest.builder()
			.partitionKey("partition key")
			.streamName(env + Topic.SERVER_LOG.getName())
			.data(SdkBytes.fromByteArray(message.toString().getBytes()))
			.build();

		kinesisAsyncClient.putRecord(request);
	}
}
