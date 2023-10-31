package com.coniverse.dangjang.domain.notification.service;

import static com.coniverse.dangjang.fixture.NotificationFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.time.LocalDate;
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

import com.coniverse.dangjang.domain.notification.dto.fluentd.FcmMessage;
import com.coniverse.dangjang.domain.notification.dto.request.CheckNotificationIdRequest;
import com.coniverse.dangjang.domain.notification.dto.response.NotificationResponse;
import com.coniverse.dangjang.domain.notification.entity.Notification;
import com.coniverse.dangjang.domain.notification.entity.UserFcmToken;
import com.coniverse.dangjang.domain.notification.repository.NotificationRepository;
import com.coniverse.dangjang.domain.notification.repository.NotificationTypeRepository;
import com.coniverse.dangjang.domain.notification.repository.UserFcmTokenRepository;
import com.coniverse.dangjang.domain.user.dto.request.PostFcmTokenRequest;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.repository.UserRepository;

import jakarta.transaction.Transactional;

/**
 * NotificationService Test
 *
 * @author EVE
 * @since 1.1.0
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
	@Autowired
	private NotificationTypeRepository notificationTypeRepository;

	private User 테오;
	private User 이브;
	private String 테오_아이디;
	private String 이브_아이디;
	private String fcmToken = "token";
	private String deviceId = "deviceId";
	private String 이브_fcmToken = "token2";
	private PostFcmTokenRequest postFcmTokenRequest = fcm_등록_및_업데이트_요청(fcmToken, deviceId);

	@Transactional
	@BeforeAll
	void setUp() {
		notificationTypeRepository.saveAll(알림_종류_엔티티_목록());
		테오 = userRepository.save(유저_테오());
		테오_아이디 = 테오.getOauthId();
		이브 = userRepository.save(유저_이브());
		이브.updateAccessedAt(LocalDate.now().minusDays(1));
		userRepository.save(이브);
		userFcmTokenRepository.save(사용자_fcmToken_엔티티(이브_fcmToken, 이브, deviceId));
		notificationRepository.saveAll(사용자_알림_엔티티_목록(테오));
	}

	@AfterAll
	void tearDown() {
		notificationRepository.deleteAll();
		userFcmTokenRepository.deleteAll();
		userRepository.deleteAll();
		notificationTypeRepository.deleteAll();
	}

	@Order(100)
	@Test
	void 유저의_알림_목록을_조회한다() {
		//given
		List<NotificationResponse> 기대_결과 = 사용자_알림_목록();

		//when
		List<NotificationResponse> 조회_결과 = notificationService.getNotificationList(테오_아이디);
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
	void 알림_확인_여부를_true로_변경한다() {
		//given
		CheckNotificationIdRequest request = 사용자_알림_확인요청_목록();
		Notification 읽은_알림 = notificationRepository.findAll().get(0);
		읽은_알림.setRead();
		notificationRepository.save(읽은_알림);

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
		assertThat(notificationService.isExistsNotReadNotification(테오_아이디)).isTrue();
	}

	@Transactional
	@Order(400)
	@Test
	void fcmToken을_저장한다() {
		//when
		notificationService.saveOrUpdateFcmToken(postFcmTokenRequest, 테오_아이디);
		//then
		UserFcmToken 조회_결과 = userFcmTokenRepository.findUserFcmTokenByFcmId(테오_아이디, postFcmTokenRequest.deviceId()).get();
		assertThat(조회_결과.getUser().getId()).isEqualTo(테오_아이디);
		assertThat(조회_결과.getFcmToken()).isEqualTo(fcmToken);
	}

	@Transactional
	@Order(450)
	@Test
	void fcmToken을_수정한다() {
		//when
		UserFcmToken userFcmToken = userFcmTokenRepository.findUserFcmTokenByFcmId(이브.getOauthId(), deviceId).get();
		String newFcmToken = "새로운_fcmToken";
		notificationService.saveOrUpdateFcmToken(fcm_등록_및_업데이트_요청(newFcmToken, deviceId), 이브.getOauthId());
		//then
		UserFcmToken 조회_결과 = userFcmTokenRepository.findUserFcmTokenByFcmId(이브.getOauthId(), deviceId).get();
		assertThat(조회_결과.getUser().getId()).isEqualTo(이브.getOauthId());
		assertThat(조회_결과.getFcmToken()).isEqualTo(newFcmToken);
	}

	@Order(500)
	@Test
	void fcmToken을_제거한다() {
		//given
		notificationService.saveOrUpdateFcmToken(postFcmTokenRequest, 테오_아이디);
		//when
		notificationService.deleteFcmToken(fcmToken);
		//then
		assertThat(userFcmTokenRepository.findUserFcmTokenByFcmId(테오_아이디, postFcmTokenRequest.deviceId())).isEmpty();
	}

	@Order(700)
	@Test
	void 접속하지_않는_유저를_위한_FcmMessage를_생성한다() {
		//given

		//when
		List<FcmMessage> fcmMessages = notificationService.makeAccessFcmMessage();
		//then
		assertThat(fcmMessages.get(0).title()).isEqualTo("오늘의 접속");
		assertThat(fcmMessages.get(0).body()).isEqualTo("오늘 접속하지 않았어요! 접속하고 포인트를 받아가세요!");
		assertThat(fcmMessages.get(0).registrationToken()).isEqualTo(이브_fcmToken);
	}

}
