package com.coniverse.dangjang.domain.guide.service;

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
import com.coniverse.dangjang.domain.guide.bloodsugar.dto.BloodSugarGuideResponse;
import com.coniverse.dangjang.domain.guide.bloodsugar.service.BloodSugarGuideGenerateService;
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
	private String 저장된_가이드_아이디;

	@Order(100)
	@Test
	void 혈당_가이드를_성공적으로_저장한다() {
		// given
		BloodSugarAnalysisData bloodSugarAnalysisData = (BloodSugarAnalysisData)bloodSugarAnalysisStrategy.analyze(
			혈당_분석_데이터(user, CommonCode.AFTER_DINNER, "140")
		);

		// when
		BloodSugarGuideResponse 가이드_응답 = (BloodSugarGuideResponse)bloodSugarGuideGenerateService.generateGuide(bloodSugarAnalysisData);
		저장된_가이드_아이디 = 가이드_응답.id();

		// then
		assertAll(
			() -> assertThat(bloodSugarAnalysisData.getAlert()).isEqualTo(가이드_응답.alert()),
			() -> assertThat(bloodSugarAnalysisData.getType().getTitle()).isEqualTo(가이드_응답.type())
		);
	}

	@Order(200)
	@Test
	void 혈당_가이드를_성공적으로_수정한다() {
		// given
		BloodSugarAnalysisData bloodSugarAnalysisData = (BloodSugarAnalysisData)bloodSugarAnalysisStrategy.analyze(
			혈당_분석_데이터(user, CommonCode.AFTER_DINNER, "200", 저장된_가이드_아이디)
		);

		// when
		BloodSugarGuideResponse 가이드_응답 = (BloodSugarGuideResponse)bloodSugarGuideGenerateService.generateGuide(bloodSugarAnalysisData);

		// then
		assertAll(
			() -> assertThat(bloodSugarAnalysisData.getAlert()).isEqualTo(가이드_응답.alert()),
			() -> assertThat(bloodSugarAnalysisData.getType().getTitle()).isEqualTo(가이드_응답.type()),
			() -> assertThat(bloodSugarAnalysisData.getGuideId()).isEqualTo(가이드_응답.id())
		);
	}

	@Order(300)
	@Test
	void 가이드_내용_검증() {
		// TODO
	}
}
