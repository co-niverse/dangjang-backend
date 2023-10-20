package com.coniverse.dangjang.domain.guide.weight.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 체중 하루 요약 가이드
 * <p>
 * 홈에 필요한 운동 가이드 정보를 가짐
 *
 * @author EVE
 * @since 1.0.0
 */
public record WeightDayGuide(String unit, double bmi, String title, @JsonIgnore String alert) {
}
