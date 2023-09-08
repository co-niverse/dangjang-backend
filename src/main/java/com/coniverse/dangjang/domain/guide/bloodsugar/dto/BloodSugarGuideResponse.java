package com.coniverse.dangjang.domain.guide.bloodsugar.dto;

import java.time.LocalDate;

import com.coniverse.dangjang.domain.analysis.enums.Alert;
import com.coniverse.dangjang.domain.guide.common.dto.GuideResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 혈당 가이드 응답 dto
 *
 * @author TEO
 * @since 1.0.0
 */
public record BloodSugarGuideResponse(@JsonIgnore String id, LocalDate createdAt, String type, Alert alert, String content) implements GuideResponse {
}
