package com.coniverse.dangjang.domain.guide.weight.dto;

import com.coniverse.dangjang.domain.guide.common.dto.GuideResponse;

/**
 * 체중 가이드 응답 dto
 *
 * @author EVE
 * @since 1.0.0
 */
public record WeightGuideResponse(String type, String createdAt, int weightDiff, String title, String content, double bmi, String unit)
	implements GuideResponse {
}
