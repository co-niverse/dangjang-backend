package com.coniverse.dangjang.domain.guide.service;

import static com.coniverse.dangjang.fixture.CommonCodeFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
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

	private Stream<Arguments> provideTypeExceptBloodSugar() {
		return Stream.of(
			Arguments.of(체중_타입(), weightGuideGenerateService)
			// TODO 운동, 당화혈색소, 식단 추가
		);
	}

	@ParameterizedTest
	@MethodSource("provideType")
	void 분석_데이터의_타입에_따라_가이드_서비스의_생성_메서드를_호출한다(List<CommonCode> type, GuideGenerateService guideGenerateService) {
		// given
		doReturn(null).when(guideGenerateService).createGuide(any());

		// when
		type.stream()
			.map(AnalysisDataFixture::분석_데이터)
			.forEach(guideService::createGuide);

		// then
		then(guideGenerateService).should(times(type.size())).createGuide(any());
	}

	@ParameterizedTest
	@MethodSource("provideType")
	void 분석_데이터의_타입에_따라_가이드_서비스의_업데이트_메서드를_호출한다(List<CommonCode> type, GuideGenerateService guideGenerateService) {
		// given
		doReturn(null).when(guideGenerateService).updateGuide(any());

		// when
		type.stream()
			.map(AnalysisDataFixture::분석_데이터)
			.forEach(guideService::updateGuide);

		// then
		then(guideGenerateService).should(times(type.size())).updateGuide(any());
	}

	@Test
	void 혈당_그룹의_타입은_타입_변경_메서드를_정상적으로_호출한다() {
		// given
		doReturn(null).when(bloodSugarGuideGenerateService).updateGuideWithType(any(), any());
		List<CommonCode> type = 혈당_타입();

		// when
		type.stream()
			.map(AnalysisDataFixture::분석_데이터)
			.forEach(data -> guideService.updateGuideWithType(data, data.getType()));

		// then
		then(bloodSugarGuideGenerateService).should(times(type.size())).updateGuideWithType(any(), any());
	}

	@ParameterizedTest
	@MethodSource("provideTypeExceptBloodSugar")
	void 혈당_그룹을_제외한_나머지_그룹의_타입은_타입_변경_메서드를_호출하면_예외가_발생한다(List<CommonCode> type, GuideGenerateService guideGenerateService) {
		// given

		// when & then
		type.stream()
			.map(AnalysisDataFixture::분석_데이터)
			.forEach(data ->
				assertThatThrownBy(() -> guideService.updateGuideWithType(data, data.getType()))
					.isInstanceOf(UnsupportedOperationException.class)
			);
	}
}
