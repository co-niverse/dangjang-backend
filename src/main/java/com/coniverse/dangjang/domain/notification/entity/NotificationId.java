package com.coniverse.dangjang.domain.notification.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

/**
 * Notification Entity의 EmbeddedId
 *
 * @author EVE
 * @since 1.0.0
 */
@Embeddable
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class NotificationId implements Serializable {
	private String oauthId;
	private String fcmToken;

	protected NotificationId(String oauthId, String fcmToken) {
		this.oauthId = oauthId;
		this.fcmToken = fcmToken;
	}
}
