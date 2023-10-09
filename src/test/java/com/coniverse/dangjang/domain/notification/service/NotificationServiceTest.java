package com.coniverse.dangjang.domain.notification.service;

import static com.coniverse.dangjang.fixture.NotificationFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coniverse.dangjang.domain.notification.dto.request.CheckNotificationIdRequest;
import com.coniverse.dangjang.domain.notification.dto.response.NotificationResponse;
import com.coniverse.dangjang.domain.notification.entity.UserFcmToken;
import com.coniverse.dangjang.domain.notification.repository.NotificationRepository;
import com.coniverse.dangjang.domain.notification.repository.UserFcmTokenRepository;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.repository.UserRepository;
import com.coniverse.dangjang.global.exception.InvalidFcmTokenException;

/**
 * NotificationService Test
 *
 * @author EVE
 * @since 1.0.0
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class NotificationServiceTest {
	@Autowired
	private NotificationRepository notificationRepository;
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserFcmTokenRepository userFcmTokenRepository;
	private User user;
	private String 유저_아이디;
	private String fcmToken = "token";

	@BeforeAll
	void setUp() {
		notificationRepository.saveAll(사용자_알림_엔티티_목록(유저_테오()));
		user = userRepository.findByOauthId(유저_테오().getOauthId()).get();
		유저_아이디 = user.getOauthId();
	}

	@AfterAll
	void tearDown() {
		notificationRepository.deleteAll();
		userFcmTokenRepository.deleteAll();
		userRepository.deleteAll();
	}

	@Order(100)
	@Test
	void 유저의_알림_목록을_조회한다() {
		//given
		List<NotificationResponse> 기대_결과 = 사용자_알림_목록();
		//when
		List<NotificationResponse> 조회_결과 = notificationService.getNotificationList(유저_아이디);
		//then
		for (int i = 0; i < 기대_결과.size(); i++) {
			NotificationResponse 결과값 = 조회_결과.get(i);
			NotificationResponse 기대값 = 기대_결과.get(i);
			assertThat(결과값.notificationId()).isEqualTo(기대값.notificationId());
			assertThat(결과값.type()).isEqualTo(기대값.type());
			assertThat(결과값.title()).isEqualTo(기대값.title());
			assertThat(결과값.content()).isEqualTo(기대값.content());
			assertThat(결과값.createdAt()).isEqualTo(기대값.createdAt());
		}

	}

	@Order(200)
	@Test
	void 알림_확인_여부를_ture로_변경한다() {
		//given
		CheckNotificationIdRequest request = 사용자_알림_확인요청_목록();
		//when
		notificationService.updateNotificationIsRead(request);
		//then
		for (Long notificationId : request.notificationIdList()) {
			assertThat(notificationRepository.findById(notificationId).get().isRead()).isTrue();
		}

	}

	@Order(300)
	@Test
	void 확인_안한_알림이_있는지_조회한다() {
		//when & then
		assertThat(notificationService.isExistsNotReadNotification(유저_아이디)).isTrue();
	}

	@Order(400)
	@Test
	void fcmToken을_저장한다() {
		//when
		notificationService.saveFcmToken(fcmToken, 유저_아이디);
		//then
		UserFcmToken 조회_결과 = userFcmTokenRepository.findById(fcmToken).get();
		assertThat(조회_결과.getOauthId()).isEqualTo(유저_아이디);
		assertThat(조회_결과.getFcmToken()).isEqualTo(fcmToken);
	}

	@Order(500)
	@Test
	void fcmToken을_제거한다() {
		//when
		notificationService.deleteFcmToken(fcmToken);
		//then
		assertThat(userFcmTokenRepository.findById(fcmToken).isEmpty()).isTrue();
	}

	@Order(600)
	@Test
	void 유효하지_않는_fcmToken을_제거하면_예외를_반환한다() {
		//given
		String 유효하지_않는_토큰 = "invalidToken";

		//when&then
		assertThatThrownBy(() -> notificationService.deleteFcmToken(유효하지_않는_토큰))
			.isInstanceOf(InvalidFcmTokenException.class);
	}

}
