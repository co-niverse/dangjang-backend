package com.coniverse.dangjang.domain.notification.service;

import static com.coniverse.dangjang.fixture.NotificationFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coniverse.dangjang.domain.notification.entity.NotificationType;
import com.coniverse.dangjang.domain.notification.entity.UserFcmToken;
import com.coniverse.dangjang.domain.notification.repository.NotificationRepository;
import com.coniverse.dangjang.domain.notification.repository.NotificationTypeRepository;
import com.coniverse.dangjang.domain.notification.repository.UserFcmTokenRepository;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.repository.UserRepository;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class NotificationSearchServiceTest {
	@Autowired
	private NotificationSearchService notificationSearchService;
	@Autowired
	private NotificationTypeRepository notificationTypeRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserFcmTokenRepository userFcmTokenRepository;
	@Autowired
	private NotificationRepository notificationRepository;

	@BeforeAll
	void setUp() throws Exception {
		User 이브 = userRepository.save(유저_이브());
		이브.updateAccessedAt(LocalDate.now().minusDays(1));
		userRepository.save(이브);
		userRepository.save(유저_테오());
		notificationTypeRepository.saveAll(알림_종류_목록());
		userFcmTokenRepository.saveAll(사용자_fcmToken_엔티티_목록());
	}

	@AfterAll
	void tearDown() {
		notificationRepository.deleteAll();
		userFcmTokenRepository.deleteAll();
		userRepository.deleteAll();
		notificationTypeRepository.deleteAll();
	}

	@Test
	void 오늘_접속_안한_유저의_fcmToken을_조회한다() {
		//given
		LocalDate date = LocalDate.now();
		//when
		List<UserFcmToken> userFcmTokens = notificationSearchService.findNotAccessUserFcmToken(date);
		//then
		assertThat(userFcmTokens).hasSize(1);
	}

	@Test
	void notificationType을_조회한다() {
		//given
		LocalDate date = LocalDate.now();
		//when
		NotificationType type = notificationSearchService.findNotificationType("접속");
		//then
		assertThat(type.getType()).isEqualTo("접속");
	}

}
