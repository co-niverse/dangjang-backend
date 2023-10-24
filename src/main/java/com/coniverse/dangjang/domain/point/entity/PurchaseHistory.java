package com.coniverse.dangjang.domain.point.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.domain.Persistable;

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
 * 포인트 상품 구매 내역 Entity
 *
 * @author EVE
 * @since 1.0.0
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PurchaseHistory implements Persistable<PointId> {
	@EmbeddedId
	@Getter(AccessLevel.PRIVATE)
	private PointId pointId;
	@Column(nullable = false)
	private String phone;
	@ColumnDefault("false")
	private boolean completed;
	@Column(nullable = false)
	private String name;
	private String comment;

	@MapsId("oauthId")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "oauth_id", insertable = false, updatable = false)
	private User user;
	@MapsId("productName")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "product_name", insertable = false, updatable = false)
	private PointProduct pointProduct;

	@Builder
	public PurchaseHistory(User user, String phone, PointProduct pointProduct, boolean completed, String name, String comment) {
		this.pointId = new PointId();
		this.phone = phone;
		this.completed = completed;
		this.user = user;
		this.pointProduct = pointProduct;
		this.name = name;
		this.comment = comment;
	}

	public String getOauthId() {
		return this.pointId.getOauthId();
	}

	public LocalDateTime getCreatedAt() {
		return this.pointId.getCreatedAt();
	}

	@Override
	public PointId getId() {
		return this.pointId;
	}

	@Override
	public boolean isNew() {
		return this.getOauthId() == null;
	}
}
