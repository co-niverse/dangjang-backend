package com.coniverse.dangjang.domain.healthmetric.entity;

import java.time.LocalDate;

import org.springframework.data.domain.Persistable;

import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.code.enums.GroupCode;
import com.coniverse.dangjang.domain.healthmetric.exception.IsNotSameGroupException;
import com.coniverse.dangjang.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
	@Enumerated(EnumType.STRING)
	private GroupCode groupCode;
	@Getter
	@Column(nullable = false)
	private String unit;
	@MapsId("oauthId")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "oauth_id")
	private User user;
	@Getter
	private String guideId;

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

	public void updateGuideId(String guideId) {
		this.guideId = guideId;
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

	/**
	 * 건강 지표 type의 그룹이 같은지 검증한다.
	 *
	 * @param type 건강 지표 type
	 * @throws IsNotSameGroupException 건강 지표 type의 그룹이 다를 경우
	 * @since 1.0.0
	 */
	public void verifySameGroupCode(CommonCode type) { // TODO test
		if (!this.groupCode.hasCode(type)) {
			throw new IsNotSameGroupException();
		}
	}
}
