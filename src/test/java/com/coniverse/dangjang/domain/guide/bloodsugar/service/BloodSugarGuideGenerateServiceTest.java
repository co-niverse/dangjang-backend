package com.coniverse.dangjang.domain.guide.bloodsugar.service;

import static com.coniverse.dangjang.fixture.AnalysisDataFixture.*;
import static com.coniverse.dangjang.fixture.CommonCodeFixture.*;
import static com.coniverse.dangjang.fixture.GuideFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coniverse.dangjang.domain.analysis.dto.healthMetric.BloodSugarAnalysisData;
import com.coniverse.dangjang.domain.analysis.strategy.BloodSugarAnalysisStrategy;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.guide.bloodsugar.document.BloodSugarGuide;
import com.coniverse.dangjang.domain.guide.bloodsugar.document.SubGuide;
import com.coniverse.dangjang.domain.guide.bloodsugar.dto.SubGuideResponse;
import com.coniverse.dangjang.domain.guide.bloodsugar.repository.BloodSugarGuideRepository;
import com.coniverse.dangjang.domain.guide.common.exception.GuideNotFoundException;
import com.coniverse.dangjang.domain.user.entity.User;

/**
 * @author TEO
 * @since 1.0.0
 */
@SpringBootTest
class BloodSugarGuideGenerateServiceTest {
	private static final User user = 유저_테오();
	@Autowired
	private BloodSugarGuideGenerateService bloodSugarGuideGenerateService;
	@Autowired
	private BloodSugarAnalysisStrategy bloodSugarAnalysisStrategy;
	@Autowired
	private BloodSugarGuideSearchService bloodSugarGuideSearchService;
	@Autowired
	private BloodSugarGuideRepository bloodSugarGuideRepository;

	@AfterEach
	void tearDown() {
		bloodSugarGuideRepository.deleteAll();
	}

	@Test
	void 혈당_가이드가_존재하지_않을_때_새로운_서브_가이드를_성공적으로_저장한다() {
		// given
		final CommonCode type = CommonCode.BEFORE_BREAKFAST;
		final String unit = "140";
		final BloodSugarAnalysisData bloodSugarAnalysisData = (BloodSugarAnalysisData)bloodSugarAnalysisStrategy.analyze(혈당_분석_데이터(user, type, unit));

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
				assertThat(서브_가이드.getType()).isEqualTo(type);
				assertThat(서브_가이드.getUnit()).isEqualTo(unit);
				assertThat(서브_가이드.getContent()).isEqualTo(서브_가이드_응답.content());
				assertThat(서브_가이드.getAlert()).isEqualTo(서브_가이드_응답.alert());
			}
		);
	}

	@Test
	void 혈당_가이드가_존재할_때_새로운_서브_가이드를_성공적으로_저장한다() {
		// given
		final CommonCode prevType = CommonCode.BEFORE_BREAKFAST;
		bloodSugarGuideGenerateService.createGuide(bloodSugarAnalysisStrategy.analyze(혈당_분석_데이터(user, prevType, "160")));

		final CommonCode curType = CommonCode.AFTER_BREAKFAST;
		final String unit = "140";
		final BloodSugarAnalysisData bloodSugarAnalysisData = (BloodSugarAnalysisData)bloodSugarAnalysisStrategy.analyze(혈당_분석_데이터(user, curType, unit));

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
				assertThat(서브_가이드.getType()).isEqualTo(curType);
				assertThat(서브_가이드.getUnit()).isEqualTo(unit);
				assertThat(서브_가이드.getContent()).isEqualTo(서브_가이드_응답.content());
				assertThat(서브_가이드.getAlert()).isEqualTo(서브_가이드_응답.alert());
			}
		);
	}

	@Test
	void 이미_존재하는_서브_가이드를_새로_저장할_때_건너뛴다() {
		// given
		final CommonCode type = CommonCode.BEFORE_BREAKFAST;
		final String unit = "140";
		bloodSugarGuideGenerateService.createGuide(bloodSugarAnalysisStrategy.analyze(혈당_분석_데이터(user, type, unit)));

		BloodSugarAnalysisData bloodSugarAnalysisData = (BloodSugarAnalysisData)bloodSugarAnalysisStrategy.analyze(혈당_분석_데이터(user, type, "200"));

		// when
		bloodSugarGuideGenerateService.createGuide(bloodSugarAnalysisData);
		BloodSugarGuide 혈당_가이드 = bloodSugarGuideSearchService.findByUserIdAndCreatedAt(user.getOauthId(), bloodSugarAnalysisData.getCreatedAt());

		// then
		assertAll(
			() -> assertThat(혈당_가이드.getSubGuides()).hasSize(1),
			() -> {
				SubGuide 서브_가이드 = 혈당_가이드.getSubGuides().get(0);
				assertThat(서브_가이드.getType()).isEqualTo(type);
				assertThat(서브_가이드.getUnit()).isEqualTo(unit);
			}
		);
	}

	@Test
	void 경보와_가이드_내용이_수정된_서브_가이드를_성공적으로_저장한다() {
		// given
		final CommonCode type = CommonCode.BEFORE_BREAKFAST;
		bloodSugarGuideGenerateService.createGuide(bloodSugarAnalysisStrategy.analyze(혈당_분석_데이터(user, type, "140")));

		final String unit = "200";
		BloodSugarAnalysisData bloodSugarAnalysisData = (BloodSugarAnalysisData)bloodSugarAnalysisStrategy.analyze(혈당_분석_데이터(user, type, unit));

		// when
		SubGuideResponse 서브_가이드_응답 = (SubGuideResponse)bloodSugarGuideGenerateService.updateGuide(bloodSugarAnalysisData);
		BloodSugarGuide 혈당_가이드 = bloodSugarGuideSearchService.findByUserIdAndCreatedAt(user.getOauthId(), bloodSugarAnalysisData.getCreatedAt());

		// then
		assertAll(
			() -> assertThat(bloodSugarAnalysisData.getAlert().getTitle()).isEqualTo(서브_가이드_응답.alert()),
			() -> assertThat(서브_가이드_응답.unit()).isNull(),
			() -> assertThat(혈당_가이드.getSubGuides()).hasSize(1),
			() -> {
				SubGuide 서브_가이드 = 혈당_가이드.getSubGuides().get(0);
				assertThat(서브_가이드.getType()).isEqualTo(type);
				assertThat(서브_가이드.getUnit()).isEqualTo(unit);
				assertThat(서브_가이드.getContent()).isEqualTo(서브_가이드_응답.content());
				assertThat(서브_가이드.getAlert()).isEqualTo(서브_가이드_응답.alert());
			}
		);
	}

	@Test
	void 수정할_서브_가이드가_존재하지_않을_때_예외를_발생한다() {
		// given
		final CommonCode validType = CommonCode.BEFORE_BREAKFAST;
		bloodSugarGuideGenerateService.createGuide(bloodSugarAnalysisStrategy.analyze(혈당_분석_데이터(user, validType, "140")));

		final CommonCode invalidType = CommonCode.BEFORE_LUNCH;
		BloodSugarAnalysisData bloodSugarAnalysisData = (BloodSugarAnalysisData)bloodSugarAnalysisStrategy.analyze(혈당_분석_데이터(user, invalidType, "200"));

		// when & then
		assertThatThrownBy(() -> bloodSugarGuideGenerateService.updateGuide(bloodSugarAnalysisData))
			.isInstanceOf(GuideNotFoundException.class);
	}

	@Test
	void 타입이_수정된_서브_가이드를_성공적으로_저장한다() {
		// given
		final CommonCode prevType = CommonCode.AFTER_BREAKFAST;
		bloodSugarGuideGenerateService.createGuide(bloodSugarAnalysisStrategy.analyze(혈당_분석_데이터(user, prevType, "140")));

		final CommonCode curType = CommonCode.ETC;
		final String unit = "200";
		BloodSugarAnalysisData bloodSugarAnalysisData = (BloodSugarAnalysisData)bloodSugarAnalysisStrategy.analyze(혈당_분석_데이터(user, curType, unit));

		// when
		SubGuideResponse 서브_가이드_응답 = (SubGuideResponse)bloodSugarGuideGenerateService.updateGuideWithType(bloodSugarAnalysisData, prevType);
		BloodSugarGuide 혈당_가이드 = bloodSugarGuideSearchService.findByUserIdAndCreatedAt(user.getOauthId(), bloodSugarAnalysisData.getCreatedAt());

		// then
		assertAll(
			() -> assertThat(bloodSugarAnalysisData.getAlert().getTitle()).isEqualTo(서브_가이드_응답.alert()),
			() -> assertThat(서브_가이드_응답.unit()).isNull(),
			() -> assertThat(혈당_가이드.getSubGuides()).hasSize(1),
			() -> {
				SubGuide 서브_가이드 = 혈당_가이드.getSubGuides().get(0);
				assertThat(서브_가이드.getType()).isEqualTo(curType);
				assertThat(서브_가이드.getUnit()).isEqualTo(unit);
				assertThat(서브_가이드.getContent()).isEqualTo(서브_가이드_응답.content());
				assertThat(서브_가이드.getAlert()).isEqualTo(서브_가이드_응답.alert());
			}
		);
	}

	@Test
	void 타입을_수정했을_때_수정_전_서브_가이드를_다시_수정하면_가이드가_존재하지_않다는_예외를_발생한다() {
		// given
		final CommonCode beforeType = CommonCode.BEFORE_BREAKFAST;
		bloodSugarGuideGenerateService.createGuide(bloodSugarAnalysisStrategy.analyze(혈당_분석_데이터(user, beforeType, "100")));
		final CommonCode curType = CommonCode.AFTER_BREAKFAST;
		bloodSugarGuideGenerateService.updateGuideWithType(bloodSugarAnalysisStrategy.analyze(혈당_분석_데이터(user, curType, "200")), beforeType);

		final CommonCode type = CommonCode.BEFORE_LUNCH;
		BloodSugarAnalysisData bloodSugarAnalysisData = (BloodSugarAnalysisData)bloodSugarAnalysisStrategy.analyze(혈당_분석_데이터(user, type, "150"));

		// when & then
		assertThatThrownBy(() -> bloodSugarGuideGenerateService.updateGuideWithType(bloodSugarAnalysisData, beforeType))
			.isInstanceOf(GuideNotFoundException.class);
	}

	@Test
	void 타입이_수정된_서브_가이드가_이미_존재할_경우_건너뛴다() {
		// given
		final CommonCode existsType = CommonCode.BEFORE_BREAKFAST;
		final String unit = "140";
		bloodSugarGuideGenerateService.createGuide(bloodSugarAnalysisStrategy.analyze(혈당_분석_데이터(user, existsType, unit)));
		final CommonCode targetType = CommonCode.AFTER_BREAKFAST;
		bloodSugarGuideGenerateService.createGuide(bloodSugarAnalysisStrategy.analyze(혈당_분석_데이터(user, targetType, "200")));

		BloodSugarAnalysisData bloodSugarAnalysisData = (BloodSugarAnalysisData)bloodSugarAnalysisStrategy.analyze(혈당_분석_데이터(user, existsType, "200"));

		// when
		bloodSugarGuideGenerateService.updateGuideWithType(bloodSugarAnalysisData, targetType);
		BloodSugarGuide 혈당_가이드 = bloodSugarGuideSearchService.findByUserIdAndCreatedAt(user.getOauthId(), bloodSugarAnalysisData.getCreatedAt());

		// then
		assertAll(
			() -> assertThat(혈당_가이드.getSubGuides()).hasSize(2),
			() -> {
				SubGuide existsSubguide = 혈당_가이드.getSubGuides().get(0);
				assertThat(existsSubguide.getUnit()).isEqualTo(unit);
				SubGuide targetSubguide = 혈당_가이드.getSubGuides().get(1);
				assertThat(targetSubguide.getType()).isEqualTo(targetType);
			}
		);
	}

	@Test
	void 서브_가이드를_저장하면_타입의_ordinal_순으로_정렬한다() {
		// given
		List<CommonCode> bloodSugarTypes = new ArrayList<>(혈당_타입());
		Collections.shuffle(bloodSugarTypes);

		// when
		BloodSugarAnalysisData bloodSugarAnalysisData = 혈당_분석_데이터(user, bloodSugarTypes.get(0), "140");
		bloodSugarTypes.forEach(type -> bloodSugarGuideGenerateService.createGuide(bloodSugarAnalysisStrategy.analyze(혈당_분석_데이터(user, type, "140"))));

		// when
		BloodSugarGuide 혈당_가이드 = bloodSugarGuideSearchService.findByUserIdAndCreatedAt(user.getOauthId(), bloodSugarAnalysisData.getCreatedAt());

		// then
		SubGuide 서브_가이드 = 혈당_가이드.getSubGuides().get(0);
		assertThat(서브_가이드.getType()).isEqualTo(CommonCode.EMPTY_STOMACH);
	}

	@Test
	void 서브_가이드를_정상적으로_삭제한다() {
		// given
		String oauthId = user.getOauthId();
		String createdAt = "2021-08-01";
		CommonCode type = CommonCode.BEFORE_BREAKFAST;
		BloodSugarGuide 혈당_가이드 = 혈당_가이드_도큐먼트(oauthId, createdAt);
		bloodSugarGuideRepository.save(혈당_가이드);

		// when
		bloodSugarGuideGenerateService.removeGuide(oauthId, LocalDate.parse(createdAt), type);

		// then
		BloodSugarGuide 삭제한_가이드 = bloodSugarGuideRepository.findByOauthIdAndCreatedAt(oauthId, createdAt).orElseThrow();
		assertThat(삭제한_가이드.getSubGuides()).hasSize(혈당_가이드.getSubGuides().size() - 1);
	}

	@Test
	void 서브_가이드를_모두_삭제하면_혈당_가이드도_삭제된다() {
		// given
		String oauthId = user.getOauthId();
		String createdAt = "2021-08-01";
		BloodSugarGuide 혈당_가이드 = 혈당_가이드_도큐먼트(oauthId, createdAt);
		bloodSugarGuideRepository.save(혈당_가이드);

		// when
		혈당_타입().forEach(type -> bloodSugarGuideGenerateService.removeGuide(oauthId, LocalDate.parse(createdAt), type));

		// then
		assertThat(bloodSugarGuideRepository.findByOauthIdAndCreatedAt(oauthId, createdAt)).isEmpty();
	}
}
