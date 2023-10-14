package com.coniverse.dangjang.global.config.batch;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.coniverse.dangjang.domain.notification.entity.Notification;
import com.coniverse.dangjang.domain.notification.entity.NotificationType;
import com.coniverse.dangjang.domain.notification.entity.UserFcmToken;
import com.coniverse.dangjang.domain.notification.mapper.NotificationMapper;
import com.coniverse.dangjang.domain.notification.repository.NotificationRepository;
import com.coniverse.dangjang.domain.notification.service.NotificationFluentbitService;
import com.coniverse.dangjang.domain.notification.service.NotificationSearchService;

import lombok.RequiredArgsConstructor;

/**
 * notification batch
 *
 * @author EVE
 * @since 1.1.0
 */
@EnableBatchProcessing
@Configuration
@RequiredArgsConstructor
public class NotificationJobConfiguration {
	private final NotificationSearchService notificationSearchService;
	private final NotificationRepository notificationRepository;
	private final NotificationFluentbitService notificationFluentbitService;
	private final NotificationMapper notificationMapper;

	@Bean
	public Job notificationJob(JobRepository jobRepository, Step simpleStep) {
		return new JobBuilder("notificationJob", jobRepository)
			.start(simpleStep)
			.build();
	}

	@Bean
	public Step accessStep(JobRepository jobRepository, Tasklet accessTasklet, PlatformTransactionManager platformTransactionManager) {
		return new StepBuilder("accessStep", jobRepository)
			.tasklet(accessTasklet, platformTransactionManager).build();
	}

	@Bean
	public Tasklet accessTasklet() {
		return ((contribution, chunkContext) -> {
			LocalDate date = LocalDate.now();
			List<UserFcmToken> userFcmTokens = notificationSearchService.findNotAccessUserFcmToken(date);
			List<String> fcmTokens = userFcmTokens.stream()
				.map(userFcmToken -> userFcmToken.getFcmToken())
				.collect(Collectors.toList());
			String title = "오늘의 접속";
			String content = "오늘 접속하지 않았어요! 접속하고 포인트를 받아가세요!";
			NotificationType notificationType = notificationSearchService.findNotificationType("접속");
			//FcmMessage fcmMessage = new FcmMessage(fcmTokens, title, content);
			//notificationFluentbitService.sendMessageToFluentbit(fcmMessage);
			List<Notification> notifications = new ArrayList<>();
			//Todo: notification에 OauthId , fcmToken 둘다 추가할 예정
			userFcmTokens.forEach(userFcmToken -> {
				notifications.add(
					notificationMapper.toEntity(userFcmToken.getUser(), title, content, date, notificationType)
				);
			});
			if (!notifications.isEmpty()) {
				notificationRepository.saveAll(notifications);
			}
			return RepeatStatus.FINISHED;
		});
	}
}