package com.coniverse.dangjang.domain.healthMetric.entity;

import java.time.LocalDate;

import org.springframework.data.domain.Persistable;

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
public class HealthMetric implements Persistable<HealthMetricId> {
	@EmbeddedId
	private HealthMetricId healthMetricId;
	@Getter
	@Column(nullable = false)
	private String unit;
	@MapsId("oauthId")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "oauth_id")
	private User user;

	@Builder
	private HealthMetric(LocalDate createdAt, HealthMetricType healthMetricType, String unit, User user) {
		this.healthMetricId = new HealthMetricId(createdAt, healthMetricType);
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

	public HealthMetricType getHealthMetricType() {
		return this.healthMetricId.getHealthMetricType();
	}

	@Override
	public HealthMetricId getId() {
		return this.healthMetricId;
	}

	@Override
	public boolean isNew() {
		return getOauthId() == null;
	}
}
