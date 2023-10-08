package com.coniverse.dangjang.fixture;

import java.time.LocalDate;

import com.coniverse.dangjang.domain.auth.dto.OauthProvider;
import com.coniverse.dangjang.domain.healthmetric.enums.HealthConnect;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.entity.enums.ActivityAmount;
import com.coniverse.dangjang.domain.user.entity.enums.Gender;
import com.coniverse.dangjang.domain.user.entity.enums.Role;
import com.coniverse.dangjang.domain.user.entity.enums.Status;

/**
 * user fixture
 *
 * @author TEO
 * @since 1.0.0
 */
public class UserFixture {
	public static User 유저_테오() {
		return User.builder()
			.oauthId("11111111")
			.oauthProvider(OauthProvider.KAKAO)
			.nickname("TEO")
			.gender(Gender.M)
			.birthday(LocalDate.of(1997, 5, 23))
			.activityAmount(ActivityAmount.MEDIUM)
			.height(199)
			.recommendedCalorie(2000)
			.role(Role.USER)
			.status(Status.ACTIVE)
			.profileImagePath("/images/profile/.png")
			.diabetic(false)
			.diabetesYear(0)
			.medicine(false)
			.injection(false)
			.build();
	}

	public static User 유저_이브() {
		return User.builder()
			.oauthId("22222222")
			.oauthProvider(OauthProvider.NAVER)
			.nickname("EVE")
			.gender(Gender.F)
			.birthday(LocalDate.of(2001, 12, 24))
			.activityAmount(ActivityAmount.LOW)
			.height(180)
			.recommendedCalorie(1500)
			.role(Role.USER)
			.status(Status.ACTIVE)
			.profileImagePath("/images/profile/.png")
			.diabetic(true)
			.diabetesYear(1)
			.medicine(false)
			.injection(false)
			.build();
	}

	public static User 전단계_환자() {
		return User.builder()
			.oauthId("33333333")
			.oauthProvider(OauthProvider.NAVER)
			.nickname("전단계임")
			.gender(Gender.F)
			.birthday(LocalDate.of(2000, 1, 1))
			.activityAmount(ActivityAmount.LOW)
			.height(180)
			.recommendedCalorie(1500)
			.role(Role.USER)
			.status(Status.ACTIVE)
			.profileImagePath("/images/profile/.png")
			.diabetic(false)
			.diabetesYear(0)
			.medicine(false)
			.injection(false)
			.build();
	}

	public static User 당뇨_환자(boolean medicine, boolean injection) {
		return User.builder()
			.oauthId("44444444")
			.oauthProvider(OauthProvider.NAVER)
			.nickname("당뇨임")
			.gender(Gender.F)
			.birthday(LocalDate.of(2000, 1, 1))
			.activityAmount(ActivityAmount.LOW)
			.height(180)
			.recommendedCalorie(1500)
			.role(Role.USER)
			.status(Status.ACTIVE)
			.profileImagePath("/images/profile/.png")
			.diabetic(true)
			.diabetesYear(0)
			.medicine(medicine)
			.injection(injection)
			.build();
	}

	public static User 포인트_유저(LocalDate accessedAt) {
		User user = 유저_이브();
		user.updateAccessedAt(accessedAt);
		return user;
	}

	public static User 헬스커넥트_연동_유저() {
		User user = 유저_이브();
		user.setHealthConnect(HealthConnect.CONNECTING);
		return user;
	}

	public static User 비활성화된_유저() {
		User user = 유저_테오();
		user.inactivate();
		return user;
	}
}
