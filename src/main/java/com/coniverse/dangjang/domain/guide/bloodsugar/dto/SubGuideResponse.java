package com.coniverse.dangjang.domain.guide.bloodsugar.dto;

import com.coniverse.dangjang.domain.guide.common.dto.GuideResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 혈당 서브 가이드 응답 dto
 *
 * @author TEO
 * @since 1.0.0
 */
public record SubGuideResponse(@JsonInclude(JsonInclude.Include.NON_NULL) String type, @JsonInclude(JsonInclude.Include.NON_NULL) String unit, String alert,
							   String title, String content) implements GuideResponse {
}
