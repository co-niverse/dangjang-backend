package com.coniverse.dangjang.domain.guide.service;

import static com.coniverse.dangjang.fixture.AnalysisDataFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.coniverse.dangjang.domain.analysis.dto.healthMetric.BloodSugarAnalysisData;
import com.coniverse.dangjang.domain.analysis.strategy.BloodSugarAnalysisStrategy;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.guide.bloodsugar.dto.SubGuideResponse;
import com.coniverse.dangjang.domain.guide.bloodsugar.factory.guideformat.CautionGuideFormat;
import com.coniverse.dangjang.domain.guide.bloodsugar.factory.guideformat.HypoglycemiaGuideFormat;
import com.coniverse.dangjang.domain.guide.bloodsugar.factory.guideformat.HypoglycemiaSuspectGuideFormat;
import com.coniverse.dangjang.domain.guide.bloodsugar.factory.guideformat.NormalGuideFormat;
import com.coniverse.dangjang.domain.guide.bloodsugar.factory.guideformat.WarningGuideFormat;
import com.coniverse.dangjang.domain.guide.bloodsugar.repository.BloodSugarGuideRepository;
import com.coniverse.dangjang.domain.guide.bloodsugar.service.BloodSugarGuideGenerateService;
import com.coniverse.dangjang.domain.user.entity.User;

/**
 * @author TEO
 * @since 1.0.0
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BloodSugarSubGuideGenerateTest {
	private static final User 전단계_환자 = 전단계_환자();
	private static final User 당뇨_환자_강하제X_주사X = 당뇨_환자(false, false);
	private static final User 당뇨_환자_강하제O_주사X = 당뇨_환자(true, false);
	private static final User 당뇨_환자_강하제X_주사O = 당뇨_환자(false, true);
	private static final User 당뇨_환자_강하제O_주사O = 당뇨_환자(true, true);
	@Autowired
	private BloodSugarGuideGenerateService bloodSugarGuideGenerateService;
	@Autowired
	private BloodSugarAnalysisStrategy bloodSugarAnalysisStrategy;
	@MockBean
	private BloodSugarGuideRepository bloodSugarGuideRepository;

	@BeforeAll
	void tearDown() {
		doReturn(null)
			.when(bloodSugarGuideRepository)
			.save(any());
	}

	private Stream<Arguments> provideHypoglycemia() {
		return Stream.of(
			Arguments.of(전단계_환자, CommonCode.EMPTY_STOMACH, "49", HypoglycemiaGuideFormat.NO_MEDICINE_AND_INJECTION),
			Arguments.of(당뇨_환자_강하제X_주사X, CommonCode.AFTER_BREAKFAST, "1", HypoglycemiaGuideFormat.NO_MEDICINE_AND_INJECTION),
			Arguments.of(당뇨_환자_강하제O_주사X, CommonCode.BEFORE_LUNCH, "30", HypoglycemiaGuideFormat.ONLY_MEDICINE),
			Arguments.of(당뇨_환자_강하제X_주사O, CommonCode.AFTER_DINNER, "20", HypoglycemiaGuideFormat.ONLY_INJECTION),
			Arguments.of(당뇨_환자_강하제O_주사O, CommonCode.BEFORE_SLEEP, "10", HypoglycemiaGuideFormat.MEDICINE_AND_INJECTION)
		);
	}

	@ParameterizedTest
	@MethodSource("provideHypoglycemia")
	void 저혈당_가이드의_제목과_내용을_올바르게_생성한다(User user, CommonCode type, String unit, HypoglycemiaGuideFormat format) {
		// given
		BloodSugarAnalysisData bloodSugarAnalysisData = (BloodSugarAnalysisData)bloodSugarAnalysisStrategy.analyze(
			혈당_분석_데이터(user, type, unit)
		);

		// when
		SubGuideResponse 서브_가이드_응답 = (SubGuideResponse)bloodSugarGuideGenerateService.createGuide(bloodSugarAnalysisData);

		// then
		assertAll(
			() -> assertThat(서브_가이드_응답.title()).isEqualTo(format.getTitle()),
			() -> assertThat(서브_가이드_응답.content()).isEqualTo(format.getContent())
		);
	}

	private Stream<Arguments> provideHypoglycemiaSuspect() {
		return Stream.of(
			Arguments.of(전단계_환자, CommonCode.EMPTY_STOMACH, "50", 20),
			Arguments.of(전단계_환자, CommonCode.AFTER_BREAKFAST, "80", 10),
			Arguments.of(당뇨_환자_강하제X_주사X, CommonCode.BEFORE_LUNCH, "65", 15),
			Arguments.of(당뇨_환자_강하제X_주사X, CommonCode.AFTER_DINNER, "72", 18)
		);
	}

	@ParameterizedTest
	@MethodSource("provideHypoglycemiaSuspect")
	void 저혈당_의심_가이드의_제목과_내용을_올바르게_생성한다(User user, CommonCode type, String unit, int deviation) {
		// given
		BloodSugarAnalysisData bloodSugarAnalysisData = (BloodSugarAnalysisData)bloodSugarAnalysisStrategy.analyze(
			혈당_분석_데이터(user, type, unit)
		);
		HypoglycemiaSuspectGuideFormat format = new HypoglycemiaSuspectGuideFormat(deviation);

		// when
		SubGuideResponse 서브_가이드_응답 = (SubGuideResponse)bloodSugarGuideGenerateService.createGuide(bloodSugarAnalysisData);
		// then
		assertAll(
			() -> assertThat(서브_가이드_응답.title()).isEqualTo(format.getTitle()),
			() -> assertThat(서브_가이드_응답.content()).isEqualTo(format.getContent())
		);
	}

	private Stream<Arguments> provideNormal() {
		return Stream.of(
			Arguments.of(전단계_환자, CommonCode.EMPTY_STOMACH, "79"),
			Arguments.of(전단계_환자, CommonCode.AFTER_BREAKFAST, "130"),
			Arguments.of(당뇨_환자_강하제X_주사X, CommonCode.BEFORE_LUNCH, "108"),
			Arguments.of(당뇨_환자_강하제X_주사X, CommonCode.AFTER_DINNER, "167")
		);
	}

	@ParameterizedTest
	@MethodSource("provideNormal")
	void 정상_가이드의_제목과_내용을_올바르게_생성한다(User user, CommonCode type, String unit) {
		// given
		BloodSugarAnalysisData bloodSugarAnalysisData = (BloodSugarAnalysisData)bloodSugarAnalysisStrategy.analyze(
			혈당_분석_데이터(user, type, unit)
		);

		// when
		SubGuideResponse 서브_가이드_응답 = (SubGuideResponse)bloodSugarGuideGenerateService.createGuide(bloodSugarAnalysisData);

		// then
		assertAll(
			() -> assertTrue(
				Arrays.stream(NormalGuideFormat.values())
					.anyMatch(t -> t.getTitle().equals(서브_가이드_응답.title()))
			),
			() -> assertThat(서브_가이드_응답.content()).isEqualTo("")
		);
	}

	private Stream<Arguments> provideCaution() {
		return Stream.of(
			Arguments.of(전단계_환자, CommonCode.EMPTY_STOMACH, "100", false, false, -1),
			Arguments.of(전단계_환자, CommonCode.AFTER_BREAKFAST, "199", false, true, -60),
			Arguments.of(당뇨_환자_강하제X_주사X, CommonCode.BEFORE_LUNCH, "145", true, false, -16),
			Arguments.of(당뇨_환자_강하제X_주사X, CommonCode.AFTER_DINNER, "229", true, true, -50)
		);
	}

	@ParameterizedTest
	@MethodSource("provideCaution")
	void 주의_가이드의_제목과_내용을_올바르게_생성한다(User user, CommonCode type, String unit, boolean lackOfExercise, boolean overweight, int deviation) {
		// given
		BloodSugarAnalysisData bloodSugarAnalysisData = (BloodSugarAnalysisData)bloodSugarAnalysisStrategy.analyze(
			혈당_분석_데이터(user, type, unit)
		);
		bloodSugarAnalysisData.setLackOfExercise(lackOfExercise);
		bloodSugarAnalysisData.setOverweight(overweight);
		CautionGuideFormat format = new CautionGuideFormat(deviation, lackOfExercise, overweight);
		format.setContent(user.isDiabetic(), type);

		// when
		SubGuideResponse 서브_가이드_응답 = (SubGuideResponse)bloodSugarGuideGenerateService.createGuide(bloodSugarAnalysisData);

		// then
		assertAll(
			() -> assertThat(서브_가이드_응답.title()).isEqualTo(format.getTitle()),
			() -> assertThat(서브_가이드_응답.content()).isEqualTo(format.getContent())
		);
	}

	private Stream<Arguments> provideWarning() {
		return Stream.of(
			Arguments.of(전단계_환자, CommonCode.EMPTY_STOMACH, "126", false, false, -27),
			Arguments.of(전단계_환자, CommonCode.AFTER_BREAKFAST, "200", false, true, -61),
			Arguments.of(당뇨_환자_강하제X_주사X, CommonCode.BEFORE_LUNCH, "150", true, false, -21),
			Arguments.of(당뇨_환자_강하제X_주사X, CommonCode.AFTER_DINNER, "230", true, true, -51)
		);
	}

	@ParameterizedTest
	@MethodSource("provideWarning")
	void 경고_의심_가이드의_제목과_내용을_올바르게_생성한다(User user, CommonCode type, String unit, boolean lackOfExercise, boolean overweight, int deviation) {
		// given
		BloodSugarAnalysisData bloodSugarAnalysisData = (BloodSugarAnalysisData)bloodSugarAnalysisStrategy.analyze(
			혈당_분석_데이터(user, type, unit)
		);
		bloodSugarAnalysisData.setLackOfExercise(lackOfExercise);
		bloodSugarAnalysisData.setOverweight(overweight);
		WarningGuideFormat format = new WarningGuideFormat(deviation, lackOfExercise, overweight);
		format.setContent(user.isDiabetic());

		// when
		SubGuideResponse 서브_가이드_응답 = (SubGuideResponse)bloodSugarGuideGenerateService.createGuide(bloodSugarAnalysisData);

		// then
		assertAll(
			() -> assertThat(서브_가이드_응답.title()).isEqualTo(format.getTitle()),
			() -> assertThat(서브_가이드_응답.content()).isEqualTo(format.getContent())
		);
	}
}
