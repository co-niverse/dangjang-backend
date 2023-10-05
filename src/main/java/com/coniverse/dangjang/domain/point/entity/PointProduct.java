package com.coniverse.dangjang.domain.point.entity;

import com.coniverse.dangjang.domain.point.enums.PointType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 포인트 상품 Entity
 *
 * @author EVE
 * @since 1.0.0
 */
@Entity
@Getter
@RequiredArgsConstructor
public class PointProduct {
	@Id
	private String product;
	private int point;
	@Enumerated(EnumType.STRING)
	@JsonIgnore
	private PointType type;

	public PointProduct(String product, int point, PointType type) {
		this.product = product;
		this.point = point;
		this.type = type;
	}
}
