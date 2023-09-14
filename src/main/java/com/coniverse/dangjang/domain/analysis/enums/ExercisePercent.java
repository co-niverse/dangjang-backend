package com.coniverse.dangjang.domain.analysis.enums;

import java.util.Arrays;

import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.global.exception.EnumNonExistentException;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 운동 칼로리 percentage
 *
 * @author TEO
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum ExercisePercent {
	WALK(0.9),
	RUN(2),
	BIKE(2.3),
	SWIM(2),
	HIKING(1.5),
	HEALTH(1.5);

	private final double percentage;

	/**
	 *
	 */
	public static double findPercentByExercise(CommonCode type) {
		return Arrays.stream(ExercisePercent.values())
			.filter(c -> c.name().equals(type.name()))
			.findFirst()
			.map(ExercisePercent::getPercentage)
			.orElseThrow(EnumNonExistentException::new);
	}
}
