package com.coniverse.dangjang.domain.healthmetric.service;

import static com.coniverse.dangjang.fixture.GuideFixture.*;
import static com.coniverse.dangjang.fixture.HealthMetricFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.guide.exercise.repository.ExerciseGuideRepository;
import com.coniverse.dangjang.domain.healthmetric.dto.response.HealthMetricChartResponse;
import com.coniverse.dangjang.domain.healthmetric.repository.HealthMetricRepository;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.repository.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 * @author EVE
 * @since 1.0.0
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class HealthMetricChartSearchServiceTest {

	@Autowired
	private HealthMetricRepository healthMetricRepository;

	@Autowired
	private ExerciseGuideRepository exerciseGuideRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private HealthMetricChartSearchService healthMetricChartSearchService;
	private User 테오;
	private LocalDate 생성_날짜 = LocalDate.of(2023, 12, 31);
	private LocalDate 시작_날짜 = LocalDate.parse("2023-12-31");
	private LocalDate 마지막_날짜 = LocalDate.parse("2024-01-06");

	@PersistenceContext
	private EntityManager em;

	@BeforeEach
	void setUp() {
		exerciseGuideRepository.deleteAll();
		healthMetricRepository.deleteAll();
	}

	@Order(100)
	@Transactional
	@Test
	void 건강지표_차트를_성공적으로_조회한다() {
		// given
		테오 = userRepository.save(유저_테오());
		em.flush();
		healthMetricRepository.saveAll(건강지표_엔티티_리스트(테오, CommonCode.AFTER_BREAKFAST, 생성_날짜, 200, 10));
		healthMetricRepository.saveAll(건강지표_엔티티_리스트(테오, CommonCode.MEASUREMENT, 생성_날짜, 40, 10));
		healthMetricRepository.saveAll(건강지표_엔티티_리스트(테오, CommonCode.STEP_COUNT, 생성_날짜, 4000, 10));

		exerciseGuideRepository.saveAll(운동가이드_리스트(테오, 생성_날짜, 200, 10));

		// when
		HealthMetricChartResponse healthMetricChartResponse = healthMetricChartSearchService.findHealthMetricChart(테오.getOauthId(), 시작_날짜,
			마지막_날짜);

		// then
		assertThat(healthMetricChartResponse.bloodSugars()).hasSize(7);
		assertThat(healthMetricChartResponse.weights()).hasSize(7);
		assertThat(healthMetricChartResponse.stepCounts()).hasSize(7);
		assertThat(healthMetricChartResponse.exerciseCalories()).hasSize(7);
	}

	@Order(200)
	@Transactional
	@Test
	void 칼로리가_0일때_제외하고_전달한다() {
		// given
		테오 = userRepository.save(유저_테오());
		em.flush();

		exerciseGuideRepository.save(걸음수_운동_가이드(테오.getOauthId(), 생성_날짜.plusDays(1)));

		// when
		HealthMetricChartResponse healthMetricChartResponse = healthMetricChartSearchService.findHealthMetricChart(테오.getOauthId(), 시작_날짜,
			마지막_날짜);

		// then
		assertThat(healthMetricChartResponse.exerciseCalories()).hasSize(0);
	}

}
