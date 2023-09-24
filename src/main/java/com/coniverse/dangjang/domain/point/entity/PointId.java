package com.coniverse.dangjang.domain.point.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EntityListeners;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * PointId
 *
 * @author EVE
 * @since 1.0.0
 */
@Getter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
@EntityListeners(AuditingEntityListener.class)
public class PointId implements Serializable {
	@Column(name = "oauth_id")
	private String oauthId;
	@Column(name = "created_at", updatable = false, nullable = false)
	private LocalDateTime createdAt = LocalDateTime.now();
	@Column(name = "product", nullable = false)
	private String product;

	protected PointId(String oauthId, String product) {
		this.oauthId = oauthId;
		this.product = product;
	}

}
