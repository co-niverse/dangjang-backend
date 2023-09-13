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
public enum ExerciseCaloriePercent {
	WALK(0.9),
	RUN(2),
	BIKE(2.3),
	SWIM(2),
	HIKING(1.5),
	HEALTH(1.5);

	private final double percent;

	/**
	 * 운동의 칼로리 percentage를 찾는다.
	 *
	 * @param type 운동 타입
	 * @return 운동의 칼로리 percentage
	 * @throws EnumNonExistentException enum이 존재하지 않을 경우
	 * @since 1.0.0
	 */
	public static double findPercentByExercise(CommonCode type) {
		return Arrays.stream(ExerciseCaloriePercent.values())
			.filter(c -> c.name().equals(type.name()))
			.findFirst()
			.map(ExerciseCaloriePercent::getPercent)
			.orElseThrow(EnumNonExistentException::new);
	}
}
