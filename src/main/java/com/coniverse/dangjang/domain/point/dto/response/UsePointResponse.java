package com.coniverse.dangjang.domain.point.dto.response;

/**
 * 포인트 사용 완료 내역 Response
 *
 * @author EVE
 * @since 1.0.0
 */
public record UsePointResponse(String phone, String type, int changePoint, int balancedPoint) {
}
