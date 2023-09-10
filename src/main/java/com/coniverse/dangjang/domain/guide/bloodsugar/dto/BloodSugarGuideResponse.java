package com.coniverse.dangjang.domain.guide.bloodsugar.dto;

import java.time.LocalDate;
import java.util.List;

import com.coniverse.dangjang.domain.guide.bloodsugar.document.TodayGuide;

/**
 * 혈당 가이드 응답 dto
 *
 * @author TEO
 * @since 1.0.0
 */
public record BloodSugarGuideResponse(LocalDate createdAt, List<TodayGuide> todayGuides, List<SubGuideResponse> guides) {
}