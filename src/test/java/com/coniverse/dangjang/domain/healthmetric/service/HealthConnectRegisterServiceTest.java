package com.coniverse.dangjang.domain.healthmetric.service;

import static com.coniverse.dangjang.fixture.HealthMetricFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.coniverse.dangjang.domain.analysis.service.AnalysisService;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.guide.common.service.GuideService;
import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthConnectPostRequest;
import com.coniverse.dangjang.domain.healthmetric.repository.HealthMetricRepository;
import com.coniverse.dangjang.domain.user.repository.UserRepository;

/**
 * @author TEO
 * @since 1.0.0
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HealthConnectRegisterServiceTest {
	@Autowired
	private HealthConnectRegisterService healthConnectRegisterService;
	@Autowired
	private HealthMetricRepository healthMetricRepository;
	@Autowired
	private UserRepository userRepository;
	@MockBean
	private GuideService guideService;
	@MockBean
	private AnalysisService analysisService;
	private String oauthId;

	@BeforeAll
	void setUp() {
		oauthId = userRepository.save(유저_테오()).getOauthId();
	}

	@AfterAll
	void tearDown() {
		healthMetricRepository.deleteAll();
		userRepository.deleteAll();
	}

	@Test
	void 헬스_커넥트_데이터_전체를_성공적으로_저장한다() {
		// given
		String type = CommonCode.BEFORE_BREAKFAST.getTitle();
		int count = 30;
		HealthConnectPostRequest request = 헬스_커넥트_등록_요청(type, count);

		// when
		healthConnectRegisterService.registerHealthConnect(request, oauthId);

		// then
		assertThat(healthMetricRepository.findAll()).hasSize(count);
	}

	@Test
	void 헬스_커넥트_데이터를_저장할_때_중복되는_데이터는_건너뛰고_저장한다() {
		// given
		String type = CommonCode.BEFORE_BREAKFAST.getTitle();
		int prevCount = 30;
		HealthConnectPostRequest prevRequest = 헬스_커넥트_등록_요청(type, prevCount);
		healthConnectRegisterService.registerHealthConnect(prevRequest, oauthId);

		// when
		int count = 50;
		HealthConnectPostRequest request = 헬스_커넥트_등록_요청(type, count);
		healthConnectRegisterService.registerHealthConnect(request, oauthId);

		// then
		assertThat(healthMetricRepository.findAll()).hasSize(count);
	}
}
