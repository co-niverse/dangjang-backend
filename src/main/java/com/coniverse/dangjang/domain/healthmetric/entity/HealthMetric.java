package com.coniverse.dangjang.domain.healthmetric.entity;

import java.time.LocalDate;

import org.springframework.data.domain.Persistable;

import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.code.enums.GroupCode;
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
	private GroupCode groupCode;
	@Getter
	@Column(nullable = false)
	private String unit;
	@MapsId("oauthId")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "oauth_id")
	private User user;

	@Builder
	private HealthMetric(LocalDate createdAt, CommonCode type, String unit, User user) {
		this.healthMetricId = new HealthMetricId(createdAt, type);
		this.groupCode = GroupCode.findByCode(type);
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

	public CommonCode getType() {
		return this.healthMetricId.getType();
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
