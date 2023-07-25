package com.coniverse.dangjang.domain.healthMetric.entity;

import java.time.LocalDate;

import com.coniverse.dangjang.domain.healthMetric.enums.HealthMetricCode;
import com.coniverse.dangjang.domain.healthMetric.enums.HealthMetricType;
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
 * 건강 지표 entity
 *
 * @author TEO
 * @since 1.0.0
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HealthMetric {
	@EmbeddedId
	@Getter
	private HealthMetricId healthMetricId;
	@Column(nullable = false)
	@Getter
	private String unit;
	// @MapsId("userId")
	@MapsId("oauthId")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "oauth_id")
	// @JoinColumns(
	// 	{
	// 		@JoinColumn(name = "oauth_id"),
	// 		@JoinColumn(name = "oauth_provider")
	// 	}
	// )
	private User user;

	@Builder
	private HealthMetric(LocalDate createdAt, HealthMetricCode healthMetricCode, HealthMetricType healthMetricType, String unit, User user) {
		this.healthMetricId = new HealthMetricId(createdAt, healthMetricCode, healthMetricType);
		this.unit = unit;
		this.user = user;
	}

	public void updateUnit(String unit) {
		this.unit = unit;
	}

	public String getOauthId() {
		return this.healthMetricId.getOauthId();
	}

	public LocalDate getCreatedAt() {
		return this.healthMetricId.getCreatedAt();
	}

	public HealthMetricCode getHealthMetricCode() {
		return this.healthMetricId.getHealthMetricCode();
	}

	public HealthMetricType getHealthMetricType() {
		return this.healthMetricId.getHealthMetricType();
	}
}
