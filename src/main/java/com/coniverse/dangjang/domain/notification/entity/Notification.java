package com.coniverse.dangjang.domain.notification.entity;

import java.time.LocalDate;

import org.hibernate.annotations.ColumnDefault;

import com.coniverse.dangjang.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Notification Entity
 *
 * @author EVE
 * @since 1.1.0
 */
@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Notification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long notificationId;
	private String title;
	private String content;

	@ColumnDefault("false")
	private boolean isRead;

	@Column(name = "created_at", nullable = false)
	private LocalDate createdAt;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "type", referencedColumnName = "type", updatable = false)
	private NotificationType notificationType;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "oauthId", referencedColumnName = "oauthId", updatable = false)
	private User user;

	@Builder
	private Notification(String title, String content, LocalDate createdAt, NotificationType notificationType, User user) {
		this.title = title;
		this.content = content;
		this.createdAt = createdAt;
		this.notificationType = notificationType;
		this.user = user;

	}

	/**
	 * notification 확인으로 변경합니다
	 *
	 * @since 1.1.0
	 */
	public void setRead() {
		this.isRead = true;
	}
}
