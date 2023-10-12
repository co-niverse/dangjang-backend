package com.coniverse.dangjang.domain.batch.config;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.coniverse.dangjang.domain.notification.service.NotificationSearchService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * notification batch
 *
 * @author EVE
 * @since 1.1.0
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class NotificationJobConfiguration {
	private final NotificationSearchService notificationSearchService;

	@Bean
	public Job notificationJob(JobRepository jobRepository, Step simpleStep) {
		return new JobBuilder("notificationJob", jobRepository)
			.start(simpleStep)
			.build();
	}

	@Bean
	public Step simpleStep(JobRepository jobRepository, Tasklet testTasklet, PlatformTransactionManager platformTransactionManager) {
		return new StepBuilder("simpleStep", jobRepository)
			.tasklet(testTasklet, platformTransactionManager).build();
	}

	@Bean
	public Tasklet testTasklet() {
		return ((contribution, chunkContext) -> {
			LocalDate date = LocalDate.now();
			List<String> fcmTokens = notificationSearchService.findNotAccessUserFcmToken(date)
				.stream()
				.map(userFcmToken -> userFcmToken.getFcmToken())
				.collect(Collectors.toList());
			String title = "오늘의 접속";
			String content = "오늘 접속하지 않았어요! 접속하고 포인트를 받아가세요!";

			fcmTokens.forEach(fcmToken -> {
				System.out.println("token: " + fcmToken);
			});
			return RepeatStatus.FINISHED;
		});
	}
}