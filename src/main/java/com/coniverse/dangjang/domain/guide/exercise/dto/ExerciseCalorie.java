package com.coniverse.dangjang.domain.guide.exercise.dto;

import com.coniverse.dangjang.domain.code.enums.CommonCode;

/**
 * 운동 가이드 칼로리 객체
 *
 * @author EVE
 * @since 1.0.0
 */
public record ExerciseCalorie(CommonCode type, int calorie) {
}
