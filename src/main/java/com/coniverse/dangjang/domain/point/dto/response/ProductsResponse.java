package com.coniverse.dangjang.domain.point.dto.response;

import java.util.Map;

/**
 * 포인트 상품 내역 조회 Response
 *
 * @author EVE
 * @since 1.0.0
 */
public record ProductsResponse(int balancedPoint, Map<String, Integer> products) {
}
