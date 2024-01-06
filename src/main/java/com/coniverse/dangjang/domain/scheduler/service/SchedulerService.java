package com.coniverse.dangjang.domain.scheduler.service;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;

import com.coniverse.dangjang.domain.notification.dto.message.FcmMessage;
import com.coniverse.dangjang.domain.notification.service.NotificationSendService;
import com.coniverse.dangjang.domain.notification.service.NotificationService;

import lombok.RequiredArgsConstructor;

/**
 * 스케쥴러
 *
 * @author EVE
 * @since 1.1.0
 */
@Service
@RequiredArgsConstructor
@EnableSchedulerLock(defaultLockAtMostFor = "PT10S")
public class SchedulerService {
	private final NotificationService notificationService;
	private final NotificationSendService notificationSendService;
	private final JobLauncher jobLauncher;
	private final Job job;

	/**
	 * 오후 6시마다 접속하지 않은 유저에게 fcmMessage를 전달한다
	 *
	 * @author EVE
	 * @since 1.1.0
	 */
	@Scheduled(cron = "0 0 18 * * *", zone = "Asia/Seoul")
	@SchedulerLock(name = "SchedulerService_makeNotification", lockAtLeastFor = "PT60S", lockAtMostFor = "PT70S")
	public void makeNotification() {
		List<FcmMessage> fcmMessage = notificationService.makeAccessFcmMessage();
		fcmMessage.forEach(notificationSendService::sendMessage);
	}
}
