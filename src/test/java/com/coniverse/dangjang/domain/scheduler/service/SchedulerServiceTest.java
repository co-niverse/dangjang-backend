package com.coniverse.dangjang.domain.scheduler.service;

import static com.coniverse.dangjang.fixture.NotificationFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coniverse.dangjang.domain.notification.repository.NotificationRepository;
import com.coniverse.dangjang.domain.notification.repository.NotificationTypeRepository;
import com.coniverse.dangjang.domain.notification.repository.UserFcmTokenRepository;
import com.coniverse.dangjang.domain.notification.service.NotificationService;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.repository.UserRepository;

/**
 * SchedulerService Test
 *
 * @author EVE
 * @since 1.1.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class SchedulerServiceTest {
	@Autowired
	private SchedulerService schedulerService;
	@Autowired
	private NotificationRepository notificationRepository;
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserFcmTokenRepository userFcmTokenRepository;
	@Autowired
	private NotificationTypeRepository notificationTypeRepository;

	private User 테오;
	private User 이브;
	private String 테오_아이디;
	private String 이브_아이디;
	private String fcmToken = "token";
	private String 이브_fcmToken = "token2";

	@BeforeAll
	void setUp() {
		notificationTypeRepository.saveAll(알림_종류_엔티티_목록());
		테오 = userRepository.save(유저_테오());
		테오_아이디 = 테오.getOauthId();
		이브 = userRepository.save(유저_이브());
		이브.updateAccessedAt(LocalDate.now().minusDays(1));
		userRepository.save(이브);
		userFcmTokenRepository.save(사용자_fcmToken_엔티티(이브_fcmToken, 이브));
		notificationRepository.saveAll(사용자_알림_엔티티_목록(테오));
	}

	@AfterAll
	void tearDown() {
		notificationRepository.deleteAll();
		userFcmTokenRepository.deleteAll();
		userRepository.deleteAll();
		notificationTypeRepository.deleteAll();
	}

	@Test
	void 스케줄러를_실행한다() {
		//given
		schedulerService.makeNotification();
		//when
		//then
		assertDoesNotThrow(() -> schedulerService.makeNotification());
	}
}
