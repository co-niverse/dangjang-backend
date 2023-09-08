package com.coniverse.dangjang.domain.analysis.strategy;

import static com.coniverse.dangjang.fixture.AnalysisDataFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coniverse.dangjang.domain.analysis.dto.healthMetric.BloodSugarAnalysisData;
import com.coniverse.dangjang.domain.analysis.enums.Alert;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.user.entity.User;

/**
 * @author TEO
 * @since 1.0.0
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BloodSugarAnalysisStrategyTest {
	public static final User 전단계_환자 = 유저_테오();
	public static final User 당뇨_환자 = 유저_이브();
	private static final CommonCode[] 공복_혈당_타입 = {CommonCode.EMPTY_STOMACH, CommonCode.BEFORE_BREAKFAST, CommonCode.BEFORE_LUNCH, CommonCode.BEFORE_DINNER};
	private static final CommonCode[] 식후_혈당_타입 = {CommonCode.AFTER_BREAKFAST, CommonCode.AFTER_LUNCH, CommonCode.AFTER_DINNER, CommonCode.BEFORE_SLEEP,
		CommonCode.ETC};
	@Autowired
	private BloodSugarAnalysisStrategy bloodSugarAnalysisStrategy;

	private Stream<Arguments> provideBloodSugarAnalysisData() {
		return Stream.of(
			Arguments.of(전단계_환자, 공복_혈당_타입, "1", "49", Alert.HYPOGLYCEMIA),
			Arguments.of(전단계_환자, 공복_혈당_타입, "50", "69", Alert.HYPOGLYCEMIA_SUSPECT),
			Arguments.of(전단계_환자, 공복_혈당_타입, "70", "99", Alert.NORMAL),
			Arguments.of(전단계_환자, 공복_혈당_타입, "100", "125", Alert.CAUTION),
			Arguments.of(전단계_환자, 공복_혈당_타입, "126", "300", Alert.WARNING),
			Arguments.of(전단계_환자, 식후_혈당_타입, "1", "49", Alert.HYPOGLYCEMIA),
			Arguments.of(전단계_환자, 식후_혈당_타입, "50", "89", Alert.HYPOGLYCEMIA_SUSPECT),
			Arguments.of(전단계_환자, 식후_혈당_타입, "90", "139", Alert.NORMAL),
			Arguments.of(전단계_환자, 식후_혈당_타입, "140", "199", Alert.CAUTION),
			Arguments.of(전단계_환자, 식후_혈당_타입, "200", "300", Alert.WARNING),
			Arguments.of(당뇨_환자, 공복_혈당_타입, "1", "49", Alert.HYPOGLYCEMIA),
			Arguments.of(당뇨_환자, 공복_혈당_타입, "50", "79", Alert.HYPOGLYCEMIA_SUSPECT),
			Arguments.of(당뇨_환자, 공복_혈당_타입, "80", "129", Alert.NORMAL),
			Arguments.of(당뇨_환자, 공복_혈당_타입, "130", "149", Alert.CAUTION),
			Arguments.of(당뇨_환자, 공복_혈당_타입, "150", "300", Alert.WARNING),
			Arguments.of(당뇨_환자, 식후_혈당_타입, "1", "49", Alert.HYPOGLYCEMIA),
			Arguments.of(당뇨_환자, 식후_혈당_타입, "50", "89", Alert.HYPOGLYCEMIA_SUSPECT),
			Arguments.of(당뇨_환자, 식후_혈당_타입, "90", "179", Alert.NORMAL),
			Arguments.of(당뇨_환자, 식후_혈당_타입, "180", "229", Alert.CAUTION),
			Arguments.of(당뇨_환자, 식후_혈당_타입, "230", "400", Alert.WARNING)
		);
	}

	@ParameterizedTest
	@MethodSource("provideBloodSugarAnalysisData")
	void 혈당_수치에_따라_알맞은_경보를_반환한다(User user, CommonCode[] types, String minUnit, String maxUnit, Alert alert) {
		assertTrue(
			Arrays.stream(types)
				.map(type -> 혈당_분석_데이터(user, type, minUnit))
				.map(bloodSugarAnalysisStrategy::analyze)
				.map(data -> (BloodSugarAnalysisData)data)
				.allMatch(data -> data.getAlert().equals(alert))
		);

		assertTrue(
			Arrays.stream(types)
				.map(type -> 혈당_분석_데이터(user, type, maxUnit))
				.map(bloodSugarAnalysisStrategy::analyze)
				.map(data -> (BloodSugarAnalysisData)data)
				.allMatch(data -> data.getAlert().equals(alert))
		);
	}
}