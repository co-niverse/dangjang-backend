package com.coniverse.dangjang.domain.point.entity;

import com.coniverse.dangjang.domain.point.enums.PointType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 포인트 상품 Entity
 *
 * @author EVE
 * @since 1.0.0
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointProduct {
	@Id
	private String productName;
	private int point;
	@JsonIgnore
	@Enumerated(EnumType.STRING)
	private PointType type;
	@JsonIgnore
	private String description;

	@Builder
	private PointProduct(String productName, int point, PointType type, String description) {
		this.productName = productName;
		this.point = point;
		this.type = type;
		this.description = description;
	}
}
