package com.coniverse.dangjang.domain.healthmetric.service;

import static com.coniverse.dangjang.fixture.HealthMetricFixture.*;
import static com.coniverse.dangjang.fixture.PointFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.coniverse.dangjang.domain.analysis.service.AnalysisService;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.guide.common.service.GuideService;
import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthConnectPostRequest;
import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthConnectRegisterRequest;
import com.coniverse.dangjang.domain.healthmetric.enums.HealthConnect;
import com.coniverse.dangjang.domain.healthmetric.repository.HealthMetricRepository;
import com.coniverse.dangjang.domain.point.repository.PointProductRepository;
import com.coniverse.dangjang.domain.point.repository.PointRepository;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.repository.UserRepository;
import com.coniverse.dangjang.domain.user.service.UserSearchService;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

/**
 * @author TEO, EVE
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
	@Autowired
	private UserSearchService userSearchService;
	@Autowired
	private PointProductRepository pointProductRepository;
	@MockBean
	private GuideService guideService;
	@MockBean
	private AnalysisService analysisService;
	@Autowired
	private EntityManager em;
	@Autowired
	private PointRepository pointRepository;

	private String oauthId;
	private User user;

	@BeforeEach
	void setUp() {
		user = userRepository.save(유저_테오());
		oauthId = user.getOauthId();
	}

	@AfterEach
	void tearDown() {
		healthMetricRepository.deleteAll();
		pointRepository.deleteAll();
		pointProductRepository.deleteAll();
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

	@Test
	void 헬스_커넥트_연동한다() {

		// given
		pointProductRepository.saveAll(전체_포인트_상품_목록());
		HealthConnectRegisterRequest request = 헬스_커넥트_연동_요청(true);

		// when
		healthConnectRegisterService.interlockHealthConnect(request, oauthId);

		User 연동된_유저 = userSearchService.findUserByOauthId(oauthId);

		// then
		assertThat(연동된_유저.getPoint()).isEqualTo(500);
	}

	@Transactional
	@Test
	void 헬스_커넥트_연동을_취소한다() {
		// given
		user.setHealthConnect(HealthConnect.CONNECTING);
		em.flush();
		pointProductRepository.saveAll(전체_포인트_상품_목록());

		HealthConnectRegisterRequest request = 헬스_커넥트_연동_요청(false);

		// when
		healthConnectRegisterService.interlockHealthConnect(request, oauthId);

		User 연동된_유저 = userSearchService.findUserByOauthId(oauthId);

		// then
		assertThat(연동된_유저.getPoint()).isZero();
		assertThat(연동된_유저.getHealthConnect()).isEqualTo(HealthConnect.DISCONNECTED);
	}

	@Transactional
	@Test
	void 헬스_커넥트_중복연동을_막는다() {
		// given
		user.setHealthConnect(HealthConnect.CONNECTING);
		em.flush();
		pointProductRepository.saveAll(전체_포인트_상품_목록());

		HealthConnectRegisterRequest request = 헬스_커넥트_연동_요청(true);

		// when
		healthConnectRegisterService.interlockHealthConnect(request, oauthId);

		User 연동된_유저 = userSearchService.findUserByOauthId(oauthId);

		// then
		assertThat(연동된_유저.getPoint()).isZero();
		assertThat(연동된_유저.getHealthConnect()).isEqualTo(HealthConnect.CONNECTING);
	}
}
