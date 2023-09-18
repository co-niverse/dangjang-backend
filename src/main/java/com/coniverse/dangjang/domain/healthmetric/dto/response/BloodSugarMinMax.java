package com.coniverse.dangjang.domain.healthmetric.dto.response;

import java.time.LocalDate;

/**
 * 하루 혈당 최저,최고 정보를 가진 객체
 * <p>
 * 건강지표 차트에 사용됨
 *
 * @author EVE
 * @since 1.0.0
 */
public record BloodSugarMinMax(LocalDate date, int minUnit, int maxUnit) {
}

