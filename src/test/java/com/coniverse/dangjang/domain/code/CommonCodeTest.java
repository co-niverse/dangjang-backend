package com.coniverse.dangjang.domain.code;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.code.enums.GroupCode;
import com.coniverse.dangjang.global.util.EnumFindUtil;

/**
 * @author TEO
 * @since 1.0.0
 */
class CommonCodeTest {
	private static Stream<Arguments> provideCommonCode() {
		return Stream.of(
			Arguments.of("취침시간", CommonCode.BED_TIME),
			Arguments.of("공복", CommonCode.EMPTY_STOMACH),
			Arguments.of("아침식전", CommonCode.BEFORE_BREAKFAST),
			Arguments.of("점심식전", CommonCode.BEFORE_LUNCH),
			Arguments.of("저녁식후", CommonCode.AFTER_DINNER),
			Arguments.of("취침전", CommonCode.BEFORE_SLEEP),
			Arguments.of("수축기", CommonCode.SYSTOLIC),
			Arguments.of("당화혈색소", CommonCode.HBA1C),
			Arguments.of("고강도운동", CommonCode.HIGH_INTENSITY_MINUTES)
		);
	}

	private static Stream<Arguments> provideCodeGroup() {
		return Stream.of(
			Arguments.of(GroupCode.SLEEP, CommonCode.BED_TIME),
			Arguments.of(GroupCode.BLOOD_SUGAR, CommonCode.EMPTY_STOMACH),
			Arguments.of(GroupCode.BLOOD_SUGAR, CommonCode.BEFORE_BREAKFAST),
			Arguments.of(GroupCode.BLOOD_SUGAR, CommonCode.BEFORE_LUNCH),
			Arguments.of(GroupCode.BLOOD_SUGAR, CommonCode.AFTER_DINNER),
			Arguments.of(GroupCode.BLOOD_SUGAR, CommonCode.BEFORE_SLEEP),
			Arguments.of(GroupCode.BLOOD_PRESSURE, CommonCode.SYSTOLIC),
			Arguments.of(GroupCode.GLYCATED_HEMOGLOBIN, CommonCode.HBA1C),
			Arguments.of(GroupCode.EXERCISE, CommonCode.HIGH_INTENSITY_MINUTES),
			Arguments.of(GroupCode.WEIGHT, CommonCode.MEASUREMENT)
		);
	}

	@ParameterizedTest
	@MethodSource("provideCommonCode")
	void 제목으로_건강지표_타입을_찾는다(String title, CommonCode type) {
		assertThat(EnumFindUtil.findByTitle(CommonCode.class, title)).isEqualTo(type);
	}

	@ParameterizedTest
	@MethodSource("provideCodeGroup")
	void 건강지표_타입으로_건강지표_코드를_찾는다(GroupCode code, CommonCode type) {
		assertThat(GroupCode.findByCode(type)).isEqualTo(code);
	}
}
