package com.coniverse.dangjang.domain.point.dto.response;

import java.util.List;

import com.coniverse.dangjang.domain.point.entity.PointProduct;

/**
 * 포인트 상품 목록 조회 Response
 *
 * @author EVE
 * @since 1.0.0
 */
public record ProductListResponse(int balancedPoint, List<PointProduct> productList) {
}
