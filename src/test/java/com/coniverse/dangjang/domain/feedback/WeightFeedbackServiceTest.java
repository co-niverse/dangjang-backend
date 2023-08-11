package com.coniverse.dangjang.domain.feedback;

import static com.coniverse.dangjang.fixture.HealthMetricFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coniverse.dangjang.domain.analysis.vo.AnalysisData.WeightAnalysisData;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.feedback.document.WeightFeedback;
import com.coniverse.dangjang.domain.feedback.repository.WeightFeedbackRepository;
import com.coniverse.dangjang.domain.feedback.service.WeightFeedbackService;
import com.coniverse.dangjang.domain.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WeightFeedbackServiceTest {
	private final LocalDate 등록_일자 = LocalDate.of(2023, 12, 31);
	@Autowired
	private WeightFeedbackService weightFeedbackService;
	@Autowired
	private WeightFeedbackRepository weightFeedbackRepository;
	@Autowired
	private UserRepository userRepository;

	private String 테오_아이디;

	@BeforeAll
	void setUp() {
		테오_아이디 = userRepository.save(유저_테오()).getOauthId();
	}

	@ParameterizedTest
	@Transactional
	@MethodSource("com.coniverse.dangjang.fixture.HealthMetricFixture#체중_건강지표_목록")
	void 체중별_조언을_성공적으로_등록한다(CommonCode commonCode, String unit) {
		// given
		WeightAnalysisData weightAnalysisData = 체중분석_데이터(commonCode, unit);
		weightFeedbackRepository.deleteAll();
		// when
		weightFeedbackService.saveFeedback(weightAnalysisData);
		// then
		WeightFeedback 등록된_건강지표 = weightFeedbackRepository.findByFeedbackId(테오_아이디, 등록_일자);
		assertThat(등록된_건강지표.getFeedback()).isNotBlank();
	}

}
