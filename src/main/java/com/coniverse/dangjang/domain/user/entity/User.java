package com.coniverse.dangjang.domain.user.entity;

import java.time.LocalDate;

import org.springframework.data.domain.Persistable;

import com.coniverse.dangjang.domain.auth.dto.OauthProvider;
import com.coniverse.dangjang.domain.healthmetric.enums.HealthConnect;
import com.coniverse.dangjang.domain.point.entity.UserPoint;
import com.coniverse.dangjang.domain.user.entity.enums.ActivityAmount;
import com.coniverse.dangjang.domain.user.entity.enums.Gender;
import com.coniverse.dangjang.domain.user.entity.enums.Role;
import com.coniverse.dangjang.domain.user.entity.enums.Status;
import com.coniverse.dangjang.global.support.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 유저 Entity
 *
 * @author EVE
 * @since 1.0.0
 */
@Entity(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity implements Persistable<String> {
	@Id
	private String oauthId;
	@Enumerated(EnumType.STRING)
	private OauthProvider oauthProvider;
	@Column(nullable = false, unique = true)
	private String nickname;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Gender gender;
	@Column(nullable = false)
	private LocalDate birthday;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ActivityAmount activityAmount;
	@Column(nullable = false)
	private int height;
	@Column(nullable = false)
	private int recommendedCalorie;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Status status = Status.ACTIVE;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role = Role.USER;
	@Column(nullable = false)
	private boolean diabetic;
	private int diabetesYear;
	private boolean medicine;
	private boolean injection;
	private String profileImagePath;
	@Enumerated(EnumType.STRING)
	private HealthConnect healthConnect = HealthConnect.NEVER_CONNECTED;
	@Column(nullable = false)
	private LocalDate accessedAt = LocalDate.now();
	private LocalDate inactivatedAt;

	@OneToOne
	@JoinColumn(name = "oauthId")
	private UserPoint userPoint;

	@Builder
	private User(String oauthId, OauthProvider oauthProvider, String nickname, Gender gender, LocalDate birthday, ActivityAmount activityAmount, int height,
		int recommendedCalorie, Role role, Status status, String profileImagePath, boolean diabetic, int diabetesYear, boolean medicine, boolean injection) {
		this.oauthId = oauthId;
		this.oauthProvider = oauthProvider;
		this.nickname = nickname;
		this.gender = gender;
		this.birthday = birthday;
		this.activityAmount = activityAmount;
		this.height = height;
		this.recommendedCalorie = recommendedCalorie;
		this.status = status;
		this.role = role;
		this.profileImagePath = profileImagePath;
		this.diabetic = diabetic;
		this.diabetesYear = diabetesYear;
		this.medicine = medicine;
		this.injection = injection;
	}

	@Override
	public String getId() {
		return this.oauthId;
	}

	@Override
	public boolean isNew() {
		return this.getCreatedAt() == null;
	}

	public void setHealthConnect(HealthConnect interlock) {
		this.healthConnect = interlock;
	}

	public void updateAccessedAt(LocalDate accessedAt) {
		this.accessedAt = accessedAt;
	}

	/**
	 * 활성화된 사용자인지 확인한다.
	 *
	 * @since 1.1.0
	 */
	public boolean isActive() {
		return this.status.equals(Status.ACTIVE);
	}

	/**
	 * 사용자를 비활성화한다.
	 *
	 * @since 1.1.0
	 */
	public void inactivate() {
		this.status = Status.INACTIVE;
		this.inactivatedAt = LocalDate.now();
	}
}
