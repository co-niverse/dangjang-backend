package com.coniverse.dangjang.domain.guide.service;

import static com.coniverse.dangjang.fixture.AnalysisDataFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coniverse.dangjang.domain.analysis.dto.healthMetric.WeightAnalysisData;
import com.coniverse.dangjang.domain.analysis.strategy.WeightAnalysisStrategy;
import com.coniverse.dangjang.domain.guide.weight.document.WeightGuide;
import com.coniverse.dangjang.domain.guide.weight.repository.WeightGuideRepository;
import com.coniverse.dangjang.domain.guide.weight.service.WeightGuideGenerateService;
import com.coniverse.dangjang.domain.user.repository.UserRepository;

/**
 * @author EVE
 * @since 1.0.0
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WeightGuideGenerateServiceTest {
	private final LocalDate 등록_일자 = LocalDate.of(2023, 12, 31);
	@Autowired
	private WeightGuideGenerateService weightGuideGenerateService;
	@Autowired
	private WeightGuideRepository weightGuideRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private WeightAnalysisStrategy weightAnalysisStrategy;
	private String 테오_아이디;
	private String 수정된_체중 = "60";

	@BeforeAll
	void setUp() {
		테오_아이디 = userRepository.save(유저_테오()).getOauthId();
	}

	@Order(300)
	@ParameterizedTest
	@ValueSource(strings = {"30", "50", "100", "120", "150"})
	void 체중별_조언을_성공적으로_등록한다(String unit) {
		// given
		weightGuideRepository.deleteAll();
		var data = weightAnalysisStrategy.analyze(체중_분석_데이터(unit));

		// when
		weightGuideGenerateService.createGuide(data);
		// then
		WeightGuide 등록된_건강지표 = weightGuideRepository.findByOauthIdAndCreatedAt(테오_아이디, 등록_일자);
		assertThat(등록된_건강지표.getTodayContent()).isEqualTo(체중_가이드_생성((WeightAnalysisData)data));
	}

	@Order(400)
	@Test
	void 체중별_조언을_성공적으로_수정한다() {
		// given
		var data = weightAnalysisStrategy.analyze(체중_분석_데이터(수정된_체중));
		// when
		weightGuideGenerateService.updateGuide(data);
		// then
		WeightGuide 등록된_건강지표 = weightGuideRepository.findByOauthIdAndCreatedAt(테오_아이디, 등록_일자);
		assertThat(등록된_건강지표.getTodayContent()).isEqualTo(체중_가이드_생성((WeightAnalysisData)data));
	}
}
