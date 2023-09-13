package com.coniverse.dangjang.domain.guide.bloodsugar.factory;

import static com.coniverse.dangjang.fixture.AnalysisDataFixture.*;
import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coniverse.dangjang.domain.analysis.enums.Alert;
import com.coniverse.dangjang.domain.guide.bloodsugar.factory.guideformat.CautionGuideFormat;
import com.coniverse.dangjang.domain.guide.bloodsugar.factory.guideformat.GuideFormat;
import com.coniverse.dangjang.domain.guide.bloodsugar.factory.guideformat.HypoglycemiaGuideFormat;
import com.coniverse.dangjang.domain.guide.bloodsugar.factory.guideformat.HypoglycemiaSuspectGuideFormat;
import com.coniverse.dangjang.domain.guide.bloodsugar.factory.guideformat.NormalGuideFormat;
import com.coniverse.dangjang.domain.guide.bloodsugar.factory.guideformat.WarningGuideFormat;

/**
 * @author TEO
 * @since 1.0.0
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BloodSugarGuideFormatFactoryTest {
	@Autowired
	private BloodSugarGuideFormatFactory bloodSugarGuideFormatFactory;

	private static Stream<Arguments> provideAlert() {
		return Stream.of(
			Arguments.of(Alert.HYPOGLYCEMIA, HypoglycemiaGuideFormat.class),
			Arguments.of(Alert.HYPOGLYCEMIA_SUSPECT, HypoglycemiaSuspectGuideFormat.class),
			Arguments.of(Alert.NORMAL, NormalGuideFormat.class),
			Arguments.of(Alert.CAUTION, CautionGuideFormat.class),
			Arguments.of(Alert.WARNING, WarningGuideFormat.class)
		);
	}

	@ParameterizedTest
	@MethodSource("provideAlert")
	void createGuideFormat(Alert alert, Class<? extends GuideFormat> expected) {
		// given
		var data = 경보_주입_혈당_분석_데이터(alert);

		// when
		GuideFormat format = bloodSugarGuideFormatFactory.createGuideFormat(data);

		// then
		assertThat(format).isInstanceOf(expected);
	}
}