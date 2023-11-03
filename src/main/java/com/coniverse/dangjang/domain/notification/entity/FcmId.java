package com.coniverse.dangjang.domain.notification.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * UserFcmToken의 PK
 *
 * @author EVE
 * @since 1.3.0
 */
@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class FcmId implements Serializable {
	private String oauthId;
	private String deviceId;

	public FcmId(String deviceId) {
		this.deviceId = deviceId;
	}

}
