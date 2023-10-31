package com.coniverse.dangjang.domain.notification.entity;

import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.global.support.BaseEntity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * UserFcmToken Entity
 * <p>
 * 사용자 모바일 기기의 fcmToken 을 저장한다
 *
 * @author EVE
 * @since 1.1.0
 */
@Getter
@Entity
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class UserFcmToken extends BaseEntity {
	@Getter(AccessLevel.PRIVATE)
	@EmbeddedId
	private FcmId fcmId;

	private String fcmToken;

	@MapsId("oauthId")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "oauth_id")
	private User user;

	@Builder
	private UserFcmToken(User user, String fcmToken, String deviceId) {
		this.fcmId = new FcmId(deviceId);
		this.fcmToken = fcmToken;
		this.user = user;
	}

	/**
	 * oquthId를 조회한다.
	 *
	 * @return oauthId
	 * @since 1.3.0
	 */
	public String getOauthId() {
		return fcmId.getOauthId();
	}

	/**
	 * fcmToken을 업데이트한다.
	 *
	 * @param fcmToken 토큰
	 * @since 1.3.0
	 */
	public void setFcmToken(String fcmToken) {
		this.fcmToken = fcmToken;
	}

}
