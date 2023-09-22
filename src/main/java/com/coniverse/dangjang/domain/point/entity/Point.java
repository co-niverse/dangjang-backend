package com.coniverse.dangjang.domain.point.entity;

import com.coniverse.dangjang.domain.point.enums.PointType;
import com.coniverse.dangjang.domain.user.entity.User;

import jakarta.persistence.Column;
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
 * Point entity
 *
 * @author EVE
 * @since 1.0.0
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Point {
	@EmbeddedId
	@Getter(AccessLevel.PRIVATE)
	private PointId pointId;
	@Column(name = "change_point", nullable = false)
	private int changePoint;
	@Column(name = "balance_point", nullable = false)
	private int balancePoint;
	@MapsId("oauthId")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "oauth_id")
	private User user;

	@Builder
	public Point(User user, PointType type, int changePoint, int balancePoint) {
		this.pointId = new PointId(user.getOauthId(), type);
		this.changePoint = changePoint;
		this.balancePoint = balancePoint;
		this.user = user;
	}

	public String getOauthId() {
		return this.pointId.getOauthId();
	}

	public PointType getType() {
		return this.pointId.getType();
	}

	public boolean isSame(int point) {
		if (this.getBalancePoint() == point) {
			return true;
		}
		return false;
	}
}
