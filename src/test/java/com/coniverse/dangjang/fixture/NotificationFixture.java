package com.coniverse.dangjang.fixture;

import static com.coniverse.dangjang.fixture.UserFixture.*;

import java.time.LocalDate;
import java.util.List;

import com.coniverse.dangjang.domain.notification.dto.request.CheckNotificationIdRequest;
import com.coniverse.dangjang.domain.notification.dto.response.NotificationResponse;
import com.coniverse.dangjang.domain.notification.entity.Notification;
import com.coniverse.dangjang.domain.notification.entity.NotificationType;
import com.coniverse.dangjang.domain.notification.entity.UserFcmToken;
import com.coniverse.dangjang.domain.user.entity.User;

/**
 * notification 관련 Fixture
 *
 * @author EVE
 * @since 1.0.0
 */
public class NotificationFixture {
	public static LocalDate 알림_생성날짜 = LocalDate.of(2023, 12, 31);

	public static List<NotificationResponse> 사용자_알림_목록() {
		return List.of(
			new NotificationResponse(1, "title1", "content1", "접속", 알림_생성날짜),
			new NotificationResponse(2, "title2", "content2", "기록", 알림_생성날짜)
		);
	}

	public static NotificationType 알림_타입_접속 = NotificationType.builder().type("접속").build();
	public static NotificationType 알림_타입_기록 = NotificationType.builder().type("기록").build();

	public static List<NotificationType> 알림_종류_목록() {
		return List.of(알림_타입_접속, 알림_타입_기록);
	}

	public static List<NotificationType> 알림_종류_엔티티_목록() {
		return List.of(NotificationType.builder().type("접속").build(), NotificationType.builder().type("기록").build());

	}

	public static List<Notification> 사용자_알림_엔티티_목록(User 유저) {
		return List.of(
			Notification.builder().notificationType(알림_타입_접속).title("title1").content("content1").createdAt(알림_생성날짜).user(유저).build(),
			Notification.builder().notificationType(알림_타입_기록).title("title2").content("content2").createdAt(알림_생성날짜).user(유저).build(),
			Notification.builder().notificationType(알림_타입_접속).title("title3").content("content3").createdAt(알림_생성날짜).user(유저).build()
		);
	}

	public static CheckNotificationIdRequest 사용자_알림_확인요청_목록() {
		return new CheckNotificationIdRequest(List.of(1L, 2L));
	}

	public static List<UserFcmToken> 사용자_fcmToken_엔티티_목록() {
		return List.of(사용자_fcmToken_엔티티("fcmToken1", 유저_이브()), 사용자_fcmToken_엔티티("fcmToken2", 유저_테오()));
	}

	public static UserFcmToken 사용자_fcmToken_엔티티(String fcmToken, User user) {
		return UserFcmToken.builder()
			.fcmToken(fcmToken)
			.user(user)
			.createdAt(LocalDate.now())
			.build();
	}

}
