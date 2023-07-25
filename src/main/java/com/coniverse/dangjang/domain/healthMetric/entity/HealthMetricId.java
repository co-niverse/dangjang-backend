package com.coniverse.dangjang.domain.healthMetric.entity;

import java.io.Serializable;
import java.time.LocalDate;

import com.coniverse.dangjang.domain.healthMetric.enums.HealthMetricCode;
import com.coniverse.dangjang.domain.healthMetric.enums.HealthMetricType;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 건강지표 복합키
 *
 * @author TEO
 * @since 1.0.0
 */
@Embeddable
@Getter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class HealthMetricId implements Serializable {
	// private UserId userId;
	private String oauthId;
	private LocalDate createdAt;
	@Enumerated(EnumType.STRING)
	private HealthMetricCode healthMetricCode;
	@Enumerated(EnumType.STRING)
	private HealthMetricType healthMetricType;

	/**
	 * HealthMetric 생성 시 사용하는 생성자
	 *
	 * @author TEO
	 * @since 1.0.0
	 */
	protected HealthMetricId(LocalDate createdAt, HealthMetricCode healthMetricCode, HealthMetricType healthMetricType) {
		this.createdAt = createdAt;
		this.healthMetricCode = healthMetricCode;
		this.healthMetricType = healthMetricType;
	}

	@Builder
	private HealthMetricId(String oauthId, LocalDate createdAt, HealthMetricCode healthMetricCode, HealthMetricType healthMetricType) {
		this.oauthId = oauthId;
		this.createdAt = createdAt;
		this.healthMetricCode = healthMetricCode;
		this.healthMetricType = healthMetricType;
	}
}
