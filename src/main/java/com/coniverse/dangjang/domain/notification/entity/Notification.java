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
@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Notification {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long notificationId;
	private String title;
	private String content;

	@ColumnDefault("false")
	private boolean isRead;

	@Column(name = "created_at", nullable = false)
	private LocalDate createdAt;

	@Column(name = "oauth_id", nullable = false)
	private String oauthId;

	@Column(name = "type", nullable = false)
	private String type;

	@MapsId("type")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "type", referencedColumnName = "type", insertable = false, updatable = false)
	private NotificationType notificationType;

	//TODO : 다대다 매핑 ,테이블 분리
	@MapsId("oauthId")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "oauth_id", referencedColumnName = "oauthId", insertable = false, updatable = false)
	private User user;

	@Builder
	private Notification(String title, String content, LocalDate createdAt, NotificationType notificationType, User user) {
		this.title = title;
		this.content = content;
		this.createdAt = createdAt;
		this.notificationType = notificationType;
		this.user = user;
		this.oauthId = user.getOauthId();
		this.type = notificationType.getType();
	}
}
