package com.coniverse.dangjang.domain.guide.bloodsugar.dto;

import java.util.List;

import com.coniverse.dangjang.domain.guide.bloodsugar.document.TodayGuide;
import com.coniverse.dangjang.domain.guide.common.dto.response.GuideResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 혈당 서브 가이드 응답 dto
 *
 * @author TEO
 * @since 1.0.0
 */
public record SubGuideResponse(String type, @JsonInclude(JsonInclude.Include.NON_NULL) String unit, String alert, String title, String content,
							   @JsonInclude(JsonInclude.Include.NON_NULL) List<TodayGuide> todayGuides) implements GuideResponse {
}
