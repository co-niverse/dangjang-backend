package com.coniverse.dangjang.domain.point.entity;

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
 * 포인트 내역 Entity (포인트 적립, 구매)
 *
 * @author EVE
 * @since 1.0.0
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PointHistory {
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
	@MapsId("product_name")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "product_name", referencedColumnName = "product_name")
	private PointProduct pointProduct;

	@Builder
	private PointHistory(User user, int changePoint, int balancePoint, PointProduct pointProduct) {
		this.pointId = new PointId(user.getOauthId(), pointProduct.getProductName());
		this.changePoint = changePoint;
		this.balancePoint = balancePoint;
		this.user = user;
		this.pointProduct = pointProduct;
	}

	/**
	 * 사용자 oauthId 조회
	 *
	 * @return String oauthId 사용자 아이디
	 * @since 1.0.0
	 */
	public String getOauthId() {
		return this.pointId.getOauthId();
	}

	/**
	 * product 조회
	 *
	 * @return String Product 포인트 상품
	 * @since 1.0.0
	 */
	public String getProductName() {
		return this.pointId.getProductName();
	}

}
