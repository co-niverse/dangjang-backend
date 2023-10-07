package com.coniverse.dangjang.domain.notification.entity;

import java.time.LocalDate;

import com.coniverse.dangjang.domain.user.entity.User;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Notification Entity
 *
 * @author EVE
 * @since 1.0.0
 */
@Getter
@Entity
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Notification {
	@EmbeddedId
	private NotificationId notificationId;
	private LocalDate createdAt;

	@MapsId("oauthId")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "oauth_id", insertable = false, updatable = false)
	private User user;

	@Builder
	private Notification(User user, String fcmToken, LocalDate createdAt) {
		this.notificationId = new NotificationId(user.getOauthId(), fcmToken);
		this.createdAt = createdAt;
		this.user = user;
	}
}
