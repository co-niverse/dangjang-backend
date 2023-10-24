package com.coniverse.dangjang.domain.point.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * 포인트 사용 Request
 *
 * @author EVE
 * @since 1.0.0
 */
public record UsePointRequest(@Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "010-XXXX-XXXX 조건에 맞는 전화번호가 필요합니다.") String phone,
							  @NotBlank(message = "상품 타입은 필수입니다.") String type, @NotBlank(message = "이름은 필수입니다.") String name,
							  String comment) {
}
