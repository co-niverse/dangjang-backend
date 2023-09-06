package com.coniverse.dangjang.domain.guide.service;

import static com.coniverse.dangjang.fixture.CommonCodeFixture.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.guide.bloodsugar.service.BloodSugarGuideGenerateService;
import com.coniverse.dangjang.domain.guide.common.service.GuideGenerateService;
import com.coniverse.dangjang.domain.guide.common.service.GuideService;
import com.coniverse.dangjang.domain.guide.weight.service.WeightGuideGenerateService;
import com.coniverse.dangjang.fixture.AnalysisDataFixture;

/**
 * @author TEO
 * @since 1.0.0
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GuideServiceTest {
	@Autowired
	private GuideService guideService;
	@SpyBean
	private BloodSugarGuideGenerateService bloodSugarGuideGenerateService;
	@SpyBean
	private WeightGuideGenerateService weightGuideGenerateService;

	private Stream<Arguments> provideType() {
		return Stream.of(
			Arguments.of(혈당_타입(), bloodSugarGuideGenerateService),
			Arguments.of(체중_타입(), weightGuideGenerateService)
			// TODO 운동, 당화혈색소, 식단 추가
		);
	}

	@ParameterizedTest
	@MethodSource("provideType")
	void 분석_데이터의_타입에_따라_알맞은_가이드_서비스를_호출한다(List<CommonCode> type, GuideGenerateService guideGenerateService) {
		// given
		doReturn(null).when(guideGenerateService).generateGuide(any());

		// when
		type.stream()
			.map(AnalysisDataFixture::분석_데이터)
			.forEach(guideService::invokeGenerateGuide);

		// then
		then(guideGenerateService).should(times(type.size())).generateGuide(any());
	}
}
