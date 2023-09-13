package com.coniverse.dangjang.domain.guide.bloodsugar.dto;

import java.util.List;

import com.coniverse.dangjang.domain.guide.bloodsugar.document.TodayGuide;

/**
 * 혈당 가이드 응답 dto
 *
 * @author TEO
 * @since 1.0.0
 */
public record BloodSugarGuideResponse(String createdAt, List<TodayGuide> todayGuides, List<SubGuideResponse> guides) {
}