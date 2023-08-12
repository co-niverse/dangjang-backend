package com.coniverse.dangjang.domain.user.entity;

import java.time.LocalDate;

import org.springframework.data.domain.Persistable;

import com.coniverse.dangjang.domain.auth.dto.OauthProvider;
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
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
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
	@Column(nullable = false, unique = true, length = 8)
	private String nickname;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 5)
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
	@Column(nullable = false)
	private boolean diabetes;
	@Column
	private int diabetes_year;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Status status;
	private String profileImagePath;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;
	@Column
	private boolean medicine;
	@Column
	private boolean injection;

	@Builder
	private User(String oauthId, OauthProvider oauthProvider, String nickname, Gender gender, LocalDate birthday, ActivityAmount activityAmount, int height,
		int recommendedCalorie, Role role, Status status, String profileImagePath, boolean diabetes, int diabetes_year, boolean medicine, boolean injection) {
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
		this.diabetes = diabetes;
		this.diabetes_year = diabetes_year;
		this.medicine = medicine;
		this.injection = injection;

	}

	@Override
	public String getId() {
		return this.oauthId;
	}

	@Override
	public boolean isNew() {
		return getCreatedAt() == null;
	}
}
