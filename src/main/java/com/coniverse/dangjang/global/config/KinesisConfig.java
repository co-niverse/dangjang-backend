package com.coniverse.dangjang.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.kinesis.KinesisAsyncClient;

/**
 * Kinesis Configuration
 *
 * @author TEO
 * @since 1.0.0
 * @deprecated fluentbit으로 대체
 */
// @Configuration
@Profile({"dev", "prod"})
@Deprecated(since = "1.0.0")
public class KinesisConfig {
	@Value("${aws.credentials.access-key}")
	private String accessKey;
	@Value("${aws.credentials.secret-key}")
	private String secretKey;

	/**
	 * kinesis client
	 *
	 * @since 1.0.0
	 */
	@Bean
	public KinesisAsyncClient kinesisAsyncClient() {
		AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(accessKey, secretKey);
		StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(awsCredentials);

		return KinesisAsyncClient.builder()
			.credentialsProvider(credentialsProvider)
			.region(Region.AP_NORTHEAST_2)
			.build();
	}
}
