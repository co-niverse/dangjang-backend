package com.coniverse.dangjang.domain.point.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;

import com.coniverse.dangjang.domain.user.entity.User;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 포인트 상품 구매 내역 Entity
 *
 * @author EVE
 * @since 1.0.0
 */
@Entity
@Getter
@RequiredArgsConstructor
public class ProductPurchase {
	@EmbeddedId
	@Getter(AccessLevel.PRIVATE)
	private PointId pointId;
	private String phone;
	@ColumnDefault("false")
	private boolean completed;

	@MapsId("oauthId")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "oauth_id")
	private User user;

	@MapsId("product")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "product", referencedColumnName = "product")
	private PointProduct pointProduct;

	@Builder
	public ProductPurchase(User user, String phone, PointProduct pointProduct, boolean completed) {
		this.pointId = new PointId(user.getOauthId(), pointProduct.getProduct());
		this.phone = phone;
		this.completed = completed;
		this.user = user;
		this.pointProduct = pointProduct;
	}

	public LocalDateTime getCreatedAt() {
		return this.pointId.getCreatedAt();
	}
}
