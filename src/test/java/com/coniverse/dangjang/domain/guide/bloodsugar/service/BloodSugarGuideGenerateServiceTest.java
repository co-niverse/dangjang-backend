package com.coniverse.dangjang.domain.guide.bloodsugar.service;

import static com.coniverse.dangjang.fixture.AnalysisDataFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coniverse.dangjang.domain.analysis.dto.healthMetric.BloodSugarAnalysisData;
import com.coniverse.dangjang.domain.analysis.strategy.BloodSugarAnalysisStrategy;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.guide.bloodsugar.document.BloodSugarGuide;
import com.coniverse.dangjang.domain.guide.bloodsugar.document.SubGuide;
import com.coniverse.dangjang.domain.guide.bloodsugar.dto.SubGuideResponse;
import com.coniverse.dangjang.domain.guide.common.exception.GuideAlreadyExistsException;
import com.coniverse.dangjang.domain.guide.common.exception.GuideNotFoundException;
import com.coniverse.dangjang.domain.user.entity.User;

/**
 * @author TEO
 * @since 1.0.0
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BloodSugarGuideGenerateServiceTest {
	private static final User user = 유저_테오();
	@Autowired
	private BloodSugarGuideGenerateService bloodSugarGuideGenerateService;
	@Autowired
	private BloodSugarAnalysisStrategy bloodSugarAnalysisStrategy;
	@Autowired
	private BloodSugarGuideSearchService bloodSugarGuideSearchService;

	@Order(100)
	@Test
	void 혈당_가이드가_존재하지_않을_때_새로운_서브_가이드를_성공적으로_저장한다() {
		// given
		BloodSugarAnalysisData bloodSugarAnalysisData = (BloodSugarAnalysisData)bloodSugarAnalysisStrategy.analyze(
			혈당_분석_데이터(user, CommonCode.BEFORE_BREAKFAST, "140")
		);

		// when
		SubGuideResponse 서브_가이드_응답 = (SubGuideResponse)bloodSugarGuideGenerateService.createGuide(bloodSugarAnalysisData);
		BloodSugarGuide 혈당_가이드 = bloodSugarGuideSearchService.findByUserIdAndCreatedAt(user.getOauthId(), bloodSugarAnalysisData.getCreatedAt());

		// then
		assertAll(
			() -> assertThat(bloodSugarAnalysisData.getAlert().getTitle()).isEqualTo(서브_가이드_응답.alert()),
			() -> assertThat(서브_가이드_응답.unit()).isNull(),
			() -> assertThat(혈당_가이드.getSubGuides()).hasSize(1),
			() -> {
				SubGuide 서브_가이드 = 혈당_가이드.getSubGuides().get(0);
				assertThat(서브_가이드.getType()).isEqualTo(bloodSugarAnalysisData.getType());
				assertThat(서브_가이드.getContent()).isEqualTo(서브_가이드_응답.content());
				assertThat(서브_가이드.getAlert()).isEqualTo(서브_가이드_응답.alert());
			}
		);
	}

	@Order(150)
	@Test
	void 혈당_가이드가_존재할_때_새로운_서브_가이드를_성공적으로_저장한다() {
		// given
		BloodSugarAnalysisData bloodSugarAnalysisData = (BloodSugarAnalysisData)bloodSugarAnalysisStrategy.analyze(
			혈당_분석_데이터(user, CommonCode.AFTER_BREAKFAST, "140")
		);

		// when
		SubGuideResponse 서브_가이드_응답 = (SubGuideResponse)bloodSugarGuideGenerateService.createGuide(bloodSugarAnalysisData);
		BloodSugarGuide 혈당_가이드 = bloodSugarGuideSearchService.findByUserIdAndCreatedAt(user.getOauthId(), bloodSugarAnalysisData.getCreatedAt());

		// then
		assertAll(
			() -> assertThat(bloodSugarAnalysisData.getAlert().getTitle()).isEqualTo(서브_가이드_응답.alert()),
			() -> assertThat(서브_가이드_응답.unit()).isNull(),
			() -> assertThat(혈당_가이드.getSubGuides()).hasSize(2),
			() -> {
				SubGuide 서브_가이드 = 혈당_가이드.getSubGuides().get(1);
				assertThat(서브_가이드.getType()).isEqualTo(bloodSugarAnalysisData.getType());
				assertThat(서브_가이드.getContent()).isEqualTo(서브_가이드_응답.content());
				assertThat(서브_가이드.getAlert()).isEqualTo(서브_가이드_응답.alert());
			}
		);
	}

	@Order(175)
	@Test
	void 이미_존재하는_서브_가이드를_새로_저장할_때_예외를_발생한다() {
		// given
		BloodSugarAnalysisData bloodSugarAnalysisData = (BloodSugarAnalysisData)bloodSugarAnalysisStrategy.analyze(
			혈당_분석_데이터(user, CommonCode.AFTER_BREAKFAST, "140")
		);

		// when & then
		assertThatThrownBy(() -> bloodSugarGuideGenerateService.createGuide(bloodSugarAnalysisData))
			.isInstanceOf(GuideAlreadyExistsException.class);
	}

	@Order(200)
	@Test
	void 경보와_가이드_내용이_수정된_서브_가이드를_성공적으로_저장한다() {
		// given
		BloodSugarAnalysisData bloodSugarAnalysisData = (BloodSugarAnalysisData)bloodSugarAnalysisStrategy.analyze(
			혈당_분석_데이터(user, CommonCode.BEFORE_BREAKFAST, "200")
		);

		// when
		SubGuideResponse 서브_가이드_응답 = (SubGuideResponse)bloodSugarGuideGenerateService.updateGuide(bloodSugarAnalysisData);
		BloodSugarGuide 혈당_가이드 = bloodSugarGuideSearchService.findByUserIdAndCreatedAt(user.getOauthId(), bloodSugarAnalysisData.getCreatedAt());

		// then
		assertAll(
			() -> assertThat(bloodSugarAnalysisData.getAlert().getTitle()).isEqualTo(서브_가이드_응답.alert()),
			() -> assertThat(서브_가이드_응답.unit()).isNull(),
			() -> assertThat(혈당_가이드.getSubGuides()).hasSize(2),
			() -> {
				SubGuide 서브_가이드 = 혈당_가이드.getSubGuides().get(0);
				assertThat(서브_가이드.getType()).isEqualTo(bloodSugarAnalysisData.getType());
				assertThat(서브_가이드.getContent()).isEqualTo(서브_가이드_응답.content());
				assertThat(서브_가이드.getAlert()).isEqualTo(서브_가이드_응답.alert());
			}
		);
	}

	@Order(225)
	@Test
	void 수정할_서브_가이드가_존재하지_않을_때_예외를_발생한다() {
		// given
		BloodSugarAnalysisData bloodSugarAnalysisData = (BloodSugarAnalysisData)bloodSugarAnalysisStrategy.analyze(
			혈당_분석_데이터(user, CommonCode.BEFORE_DINNER, "200")
		);

		// when & then
		assertThatThrownBy(() -> bloodSugarGuideGenerateService.updateGuide(bloodSugarAnalysisData))
			.isInstanceOf(GuideNotFoundException.class);
	}

	@Order(250)
	@Test
	void 타입이_수정된_서브_가이드를_성공적으로_저장한다() {
		// given
		CommonCode 수정할_타입 = CommonCode.AFTER_BREAKFAST;
		BloodSugarAnalysisData bloodSugarAnalysisData = (BloodSugarAnalysisData)bloodSugarAnalysisStrategy.analyze(
			혈당_분석_데이터(user, CommonCode.ETC, "200")
		);

		// when
		SubGuideResponse 서브_가이드_응답 = (SubGuideResponse)bloodSugarGuideGenerateService.updateGuideWithType(bloodSugarAnalysisData, 수정할_타입);
		BloodSugarGuide 혈당_가이드 = bloodSugarGuideSearchService.findByUserIdAndCreatedAt(user.getOauthId(), bloodSugarAnalysisData.getCreatedAt());

		// then
		assertAll(
			() -> assertThat(bloodSugarAnalysisData.getAlert().getTitle()).isEqualTo(서브_가이드_응답.alert()),
			() -> assertThat(서브_가이드_응답.unit()).isNull(),
			() -> assertThat(혈당_가이드.getSubGuides()).hasSize(2),
			() -> {
				SubGuide 서브_가이드 = 혈당_가이드.getSubGuides().get(1);
				assertThat(서브_가이드.getType()).isEqualTo(bloodSugarAnalysisData.getType());
				assertThat(서브_가이드.getContent()).isEqualTo(서브_가이드_응답.content());
				assertThat(서브_가이드.getAlert()).isEqualTo(서브_가이드_응답.alert());
			}
		);
	}

	@Order(275)
	@Test
	void 타입을_수정했을_때_수정_전_서브_가이드를_다시_수정하면_가이드가_존재하지_않다는_예외를_발생한다() {
		// given
		BloodSugarAnalysisData bloodSugarAnalysisData = (BloodSugarAnalysisData)bloodSugarAnalysisStrategy.analyze(
			혈당_분석_데이터(user, CommonCode.BEFORE_LUNCH, "200")
		);

		// when & then
		assertThatThrownBy(() -> bloodSugarGuideGenerateService.updateGuideWithType(bloodSugarAnalysisData, CommonCode.AFTER_BREAKFAST))
			.isInstanceOf(GuideNotFoundException.class);
	}

	@Order(300)
	@Test
	void 타입이_수정된_서브_가이드가_이미_존재할_경우_예외를_발생한다() {
		// given
		CommonCode 수정할_타입 = CommonCode.ETC;
		BloodSugarAnalysisData bloodSugarAnalysisData = (BloodSugarAnalysisData)bloodSugarAnalysisStrategy.analyze(
			혈당_분석_데이터(user, CommonCode.BEFORE_BREAKFAST, "200")
		);

		// when & then
		assertThatThrownBy(() -> bloodSugarGuideGenerateService.updateGuideWithType(bloodSugarAnalysisData, 수정할_타입))
			.isInstanceOf(GuideAlreadyExistsException.class);
	}

	@Order(400)
	@Test
	void 서브_가이드를_저장하면_타입의_ordinal_순으로_정렬한다() {
		// given
		BloodSugarAnalysisData bloodSugarAnalysisData = (BloodSugarAnalysisData)bloodSugarAnalysisStrategy.analyze(
			혈당_분석_데이터(user, CommonCode.EMPTY_STOMACH, "140")
		);

		// when
		SubGuideResponse 서브_가이드_응답 = (SubGuideResponse)bloodSugarGuideGenerateService.createGuide(bloodSugarAnalysisData);
		BloodSugarGuide 혈당_가이드 = bloodSugarGuideSearchService.findByUserIdAndCreatedAt(user.getOauthId(), bloodSugarAnalysisData.getCreatedAt());

		// then
		assertAll(
			() -> assertThat(혈당_가이드.getSubGuides()).hasSize(3),
			() -> {
				SubGuide 서브_가이드 = 혈당_가이드.getSubGuides().get(0);
				assertThat(서브_가이드.getType()).isEqualTo(CommonCode.EMPTY_STOMACH);
			}
		);
	}
}
