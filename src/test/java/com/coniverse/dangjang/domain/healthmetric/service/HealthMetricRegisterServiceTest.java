package com.coniverse.dangjang.domain.healthmetric.service;

import static com.coniverse.dangjang.fixture.HealthMetricFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.coniverse.dangjang.domain.analysis.service.AnalysisService;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.guide.common.service.GuideService;
import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthMetricPatchRequest;
import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthMetricPostRequest;
import com.coniverse.dangjang.domain.healthmetric.dto.response.HealthMetricResponse;
import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.healthmetric.exception.HealthMetricNotFoundException;
import com.coniverse.dangjang.domain.healthmetric.repository.HealthMetricRepository;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.repository.UserRepository;
import com.coniverse.dangjang.global.util.EnumFindUtil;

/**
 * @author TEO
 * @since 1.0.0
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HealthMetricRegisterServiceTest {
	@Autowired
	private HealthMetricRegisterService healthMetricRegisterService;
	@Autowired
	private HealthMetricRepository healthMetricRepository;
	@Autowired
	private UserRepository userRepository;
	@MockBean
	private AnalysisService analysisService;
	@MockBean
	private GuideService guideService;
	private User user;
	private String oauthId;
	private HealthMetric 등록된_건강지표;
	private HealthMetric 수정된_건강지표;

	@BeforeAll
	void setUp() {
		user = userRepository.save(유저_테오());
		oauthId = user.getOauthId();
	}

	@AfterAll
	void tearDown() {
		healthMetricRepository.deleteAll();
		userRepository.deleteAll();
	}

	@Order(50)
	@Test
	void 건강지표를_성공적으로_등록한다() {
		// given
		HealthMetricPostRequest request = 건강지표_등록_요청();

		// when
		HealthMetricResponse response = healthMetricRegisterService.register(request, oauthId);

		// then
		등록된_건강지표 = healthMetricRepository
			.findByHealthMetricId(oauthId, response.createdAt(), EnumFindUtil.findByTitle(CommonCode.class, response.type())).orElseThrow();

		assertAll(
			() -> assertThat(등록된_건강지표.getType().getTitle()).isEqualTo(request.type()),
			() -> assertThat(등록된_건강지표.getUnit()).isEqualTo(request.unit()),
			() -> assertThat(등록된_건강지표.getCreatedAt()).isEqualTo(request.createdAt()),
			() -> assertThat(등록된_건강지표.getOauthId()).isEqualTo(oauthId)
		);
	}

	@Order(100)
	@Test
	void 단위만_변경된_건강지표를_성공적으로_수정한다() {
		// given
		HealthMetricPatchRequest request = 단위_변경한_건강지표_수정_요청();

		// when
		HealthMetricResponse response = healthMetricRegisterService.update(request, oauthId);

		// then

		HealthMetric 수정된_건강지표 = healthMetricRepository
			.findByHealthMetricId(oauthId, response.createdAt(), EnumFindUtil.findByTitle(CommonCode.class, response.type())).orElseThrow();

		assertAll(
			() -> assertThat(수정된_건강지표.getUnit()).isNotEqualTo(등록된_건강지표.getUnit()),
			() -> assertThat(수정된_건강지표.getUnit()).isEqualTo(request.unit()),
			() -> assertThat(수정된_건강지표.getCreatedAt()).isEqualTo(등록된_건강지표.getCreatedAt()),
			() -> assertThat(수정된_건강지표.getOauthId()).isEqualTo(등록된_건강지표.getOauthId()),
			() -> assertThat(수정된_건강지표.getType()).isEqualTo(등록된_건강지표.getType())
		);
	}

	@Order(200)
	@Test
	void 타입이_변경된_건강지표를_성공적으로_수정한다() {
		// given
		HealthMetricPatchRequest request = 타입_변경한_건강지표_수정_요청();

		// when
		HealthMetricResponse response = healthMetricRegisterService.update(request, oauthId);

		// then
		수정된_건강지표 = healthMetricRepository
			.findByHealthMetricId(oauthId, response.createdAt(), EnumFindUtil.findByTitle(CommonCode.class, response.type())).orElseThrow();

		assertAll(
			() -> assertThat(수정된_건강지표.getType().getTitle()).isEqualTo(request.newType()),
			() -> assertThat(수정된_건강지표.getCreatedAt()).isEqualTo(등록된_건강지표.getCreatedAt()),
			() -> assertThat(수정된_건강지표.getOauthId()).isEqualTo(등록된_건강지표.getOauthId()),
			() -> assertThat(수정된_건강지표.getType()).isNotEqualTo(등록된_건강지표.getType())
		);
	}

	@Order(300)
	@Test
	void 건강지표를_성공적으로_삭제한다() {
		// given
		CommonCode type = 수정된_건강지표.getType();
		LocalDate createdAt = 수정된_건강지표.getCreatedAt();

		// when
		healthMetricRegisterService.remove(createdAt.toString(), type.getTitle(), oauthId);

		// then
		assertThat(healthMetricRepository.findByHealthMetricId(oauthId, createdAt, type)).isEmpty();
	}

	@Order(400)
	@Test
	void 존재하지_않는_건강지표를_삭제할_경우_예외가_발생한다() {
		// given
		String type = 수정된_건강지표.getType().getTitle();
		String createdAt = 수정된_건강지표.getCreatedAt().toString();

		// when & then
		assertThatThrownBy(() -> healthMetricRegisterService.remove(createdAt, type, oauthId))
			.isInstanceOf(HealthMetricNotFoundException.class);
	}
}
