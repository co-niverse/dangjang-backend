package com.coniverse.dangjang.domain.guide.exercise.document;

import com.coniverse.dangjang.domain.code.enums.CommonCode;

/**
 * 운동 소모 칼로리
 *
 * @author EVE
 * @since 1.0.0
 */
public record ExerciseCalorie(CommonCode type, int calorie, int exerciseTime) {
}
