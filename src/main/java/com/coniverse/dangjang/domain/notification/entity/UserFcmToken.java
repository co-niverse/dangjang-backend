package com.coniverse.dangjang.domain.notification.entity;

import java.time.LocalDate;

import com.coniverse.dangjang.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * UserFcmToken Entity
 * <p>
 * 사용자 모바일 기기의 fcmToken을 저장한다
 *
 * @author EVE
 * @since 1.0.0
 */
@Getter
@Entity
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class UserFcmToken {
	@Id
	@Column(name = "fcm_token", nullable = false)
	private String fcmToken;

	private LocalDate createdAt;
	@Column(name = "oauth_id", nullable = false)
	private String oauthId;

	@MapsId("oauthId")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "oauth_id", referencedColumnName = "oauthId", insertable = false, updatable = false)
	private User user;

	@Builder
	private UserFcmToken(User user, String fcmToken, LocalDate createdAt) {
		this.fcmToken = fcmToken;
		this.createdAt = createdAt;
		this.user = user;
		this.oauthId = user.getOauthId();
	}
}
