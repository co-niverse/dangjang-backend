package com.coniverse.dangjang.domain.feedback;

import static com.coniverse.dangjang.fixture.HealthMetricFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.params.provider.Arguments.*;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coniverse.dangjang.domain.analysis.document.WeightFeedback;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.feedback.repository.WeightFeedbackRepository;
import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthMetricPostRequest;
import com.coniverse.dangjang.domain.healthmetric.dto.response.HealthMetricResponse;
import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.healthmetric.repository.HealthMetricRepository;
import com.coniverse.dangjang.domain.healthmetric.service.HealthMetricRegistrationService;
import com.coniverse.dangjang.domain.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WeightFeedbackServiceTest {
	private final LocalDate 등록_일자 = LocalDate.of(2023, 12, 31);
	@Autowired
	private HealthMetricRegistrationService healthMetricRegistrationService;
	@Autowired
	private HealthMetricRepository healthMetricRepository;
	@Autowired
	private WeightFeedbackRepository weightFeedbackRepository;
	@Autowired
	private UserRepository userRepository;

	private String 테오_아이디;
	private HealthMetric 등록된_건강지표;

	static Stream<Arguments> 체중_건강지표_목록() {
		return Stream.of(arguments(CommonCode.WT_MEM, "30"), arguments(CommonCode.WT_MEM, "50"), arguments(CommonCode.WT_MEM, "100"),
			arguments(CommonCode.WT_MEM, "120"), arguments(CommonCode.WT_MEM, "150"));
	}

	@BeforeAll
	void setUp() {
		테오_아이디 = userRepository.save(유저_테오()).getOauthId();
	}

	@ParameterizedTest
	@Transactional
	@MethodSource("체중_건강지표_목록")
	void 체중별_조언을_성공적으로_등록한다(CommonCode commonCode, String unit) {
		// given
		HealthMetricPostRequest request = 건강지표_등록_요청(commonCode, unit);
		weightFeedbackRepository.deleteByFeedbackId(테오_아이디, 등록_일자);

		// when
		HealthMetricResponse response = healthMetricRegistrationService.register(request, 등록_일자, 테오_아이디);

		// then
		WeightFeedback 등록된_건강지표 = weightFeedbackRepository.findByFeedbackId(테오_아이디, response.createdAt());

		assertThat(등록된_건강지표.getFeedback()).isNotBlank();
	}

}
