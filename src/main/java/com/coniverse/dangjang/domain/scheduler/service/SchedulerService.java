package com.coniverse.dangjang.domain.scheduler.service;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.notification.dto.fluentd.FcmMessage;
import com.coniverse.dangjang.domain.notification.service.NotificationFluentbitService;
import com.coniverse.dangjang.domain.notification.service.NotificationService;

import lombok.RequiredArgsConstructor;

/**
 * SchedulerService
 *
 * @author EVE
 * @since 1.1.0
 */
@RequiredArgsConstructor
@Service
public class SchedulerService {
	private final NotificationService notificationService;
	private final NotificationFluentbitService notificationFluentbitService;
	private final JobLauncher jobLauncher;
	private final Job job;

	/**
	 * 오후 6시마다 접속하지 않은 유저에게 fcmMessage를 전달한다
	 *
	 * @author EVE
	 * @since 1.1.0
	 */
	@Scheduled(cron = "0 0 18 * * *", zone = "Asia/Seoul")
	public void makeNotification() {
		List<FcmMessage> fcmMessage = notificationService.makeAccessFcmMessage();
		fcmMessage.forEach(message -> {
			notificationFluentbitService.sendMessageToFluentbit(message);
		});

	}
}
