package com.coniverse.dangjang.domain.code;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.coniverse.dangjang.domain.code.enums.CodeGroup;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.global.util.EnumFindUtil;

/**
 * @author TEO
 * @since 1.0.0
 */
class CommonCodeTest {
	private static Stream<Arguments> provideCommonCode() {
		return Stream.of(
			Arguments.of("취침시간", CommonCode.SL_BTM),
			Arguments.of("공복", CommonCode.BS_ESM),
			Arguments.of("아침식전", CommonCode.BS_BBF),
			Arguments.of("점심식전", CommonCode.BS_BLC),
			Arguments.of("저녁식후", CommonCode.BS_ADN),
			Arguments.of("취침전", CommonCode.BS_BSL),
			Arguments.of("수축기", CommonCode.BP_SYS),
			Arguments.of("당화혈색소", CommonCode.GH_A1C),
			Arguments.of("고강도운동", CommonCode.EC_HIM)
		);
	}

	private static Stream<Arguments> provideCodeGroup() {
		return Stream.of(
			Arguments.of(CodeGroup.SL, CommonCode.SL_BTM),
			Arguments.of(CodeGroup.BS, CommonCode.BS_ESM),
			Arguments.of(CodeGroup.BS, CommonCode.BS_BBF),
			Arguments.of(CodeGroup.BS, CommonCode.BS_BLC),
			Arguments.of(CodeGroup.BS, CommonCode.BS_ADN),
			Arguments.of(CodeGroup.BS, CommonCode.BS_BSL),
			Arguments.of(CodeGroup.BP, CommonCode.BP_SYS),
			Arguments.of(CodeGroup.GH, CommonCode.GH_A1C),
			Arguments.of(CodeGroup.EC, CommonCode.EC_HIM),
			Arguments.of(CodeGroup.WT, CommonCode.WT_MEM)
		);
	}

	@ParameterizedTest
	@MethodSource("provideCommonCode")
	void 제목으로_건강지표_타입을_찾는다(String title, CommonCode type) {
		assertThat(EnumFindUtil.findByTitle(CommonCode.class, title)).isEqualTo(type);
	}

	@ParameterizedTest
	@MethodSource("provideCodeGroup")
	void 건강지표_타입으로_건강지표_코드를_찾는다(CodeGroup code, CommonCode type) {
		assertThat(CodeGroup.findByCode(type)).isEqualTo(code);
	}
}
