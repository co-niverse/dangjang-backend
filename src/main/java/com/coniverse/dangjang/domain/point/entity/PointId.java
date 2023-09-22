package com.coniverse.dangjang.domain.point.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.coniverse.dangjang.domain.point.enums.PointType;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
	@CreatedDate
	@Column(name = "created_at", updatable = false, nullable = false)
	private LocalDateTime createdAt = LocalDateTime.now();
	@Enumerated(EnumType.STRING)
	private PointType type;

	protected PointId(String oauthId, PointType type) {
		this.oauthId = oauthId;
		this.type = type;
	}

}
