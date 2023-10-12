package com.coniverse.dangjang.domain.scheduler.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SchedulerService {
	@Scheduled(cron = "0 0 20 * * *", zone = "Asia/Seoul")
	public void makeNotification() {
		//스케줄러 실행
	}
}
