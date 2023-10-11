package com.coniverse.dangjang.domain.point.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode
public class PointId implements Serializable {
	private String oauthId;
	private String productName;
	@Column(updatable = false, nullable = false)
	private final LocalDateTime createdAt = LocalDateTime.now();
}
