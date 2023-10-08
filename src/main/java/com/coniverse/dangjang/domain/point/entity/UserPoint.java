package com.coniverse.dangjang.domain.point.entity;

import com.coniverse.dangjang.domain.user.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 유저 포인트 Entity
 *
 * @author EVE
 * @since 1.0.0
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPoint {
	@Id
	private String oauthId;
	private int point;
	@Version
	private Long version;
	@OneToOne(mappedBy = "userPoint")
	private User user;

	@Builder
	private UserPoint(String oauthId, int point) {
		this.oauthId = oauthId;
		this.point = point;
	}

	public void setPoint(int point) {
		this.point = point;
	}
}
