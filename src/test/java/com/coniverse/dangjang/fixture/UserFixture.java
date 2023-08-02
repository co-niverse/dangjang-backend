package com.coniverse.dangjang.fixture;

import java.time.LocalDate;

import com.coniverse.dangjang.domain.auth.dto.OauthProvider;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.entity.enums.ActivityAmount;
import com.coniverse.dangjang.domain.user.entity.enums.Gender;
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
			.role("ROLE_USER")
			.status(Status.ACTIVE)
			.profileImagePath("/images/profile/.png")
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
			.role("ROLE_USER")
			.status(Status.ACTIVE)
			.profileImagePath("/images/profile/.png")
			.build();
	}
}
