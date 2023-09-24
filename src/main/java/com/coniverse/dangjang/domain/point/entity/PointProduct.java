package com.coniverse.dangjang.domain.point.entity;

import com.coniverse.dangjang.domain.point.enums.PointType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 포인트 상품 Entity
 *
 * @author EVE
 * @since 1.0.0
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PointProduct {
	@Id
	private String product;
	private int point;
	@Enumerated(EnumType.STRING)
	private PointType type;
}
