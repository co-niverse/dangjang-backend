package com.coniverse.dangjang.domain.notification.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * notification type Entity
 * <p>
 * 알림 종류
 *
 * @author EVE
 * @since 1.0.0
 */
@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class NotificationType {
	@Id
	private String type;

	@Builder
	private NotificationType(String type) {
		this.type = type;
	}
}
