package com.coniverse.dangjang.domain.healthmetric.service;

import static com.coniverse.dangjang.fixture.HealthMetricFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.healthmetric.exception.HealthMetricNotFoundException;
import com.coniverse.dangjang.domain.healthmetric.repository.HealthMetricRepository;
import com.coniverse.dangjang.domain.user.entity.User;

import jakarta.transaction.Transactional;

/**
 * @author EVE
 * @since 1.0.0
 */
@Transactional
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HealthMetricSearchServiceTest {
	@Autowired
	private HealthMetricRepository healthMetricRepository;
	@Autowired
	private HealthMetricSearchService healthMetricSearchService;
	private User user;

	private HealthMetric 최근_건강지표;
	private HealthMetric 조회할_건강지표;
	private LocalDate 시작_날짜;
	private LocalDate 종료_날짜;

	@BeforeAll
	void setUp() {
		user = 유저_테오();
		시작_날짜 = LocalDate.of(2021, 8, 1);
		List<HealthMetric> healthMetrics = new ArrayList<>();
		조회할_건강지표 = 건강지표_엔티티(user, CommonCode.STEP_COUNT, "10000", 시작_날짜);
		종료_날짜 = 시작_날짜.plusDays(9);
		최근_건강지표 = 건강지표_엔티티(user, CommonCode.STEP_COUNT, "1000", 종료_날짜);

		for (int i = 0; i < 10; i++) {
			String unit = String.valueOf(10000 - i * 1000);
			healthMetrics.add(건강지표_엔티티(user, CommonCode.STEP_COUNT, unit, 시작_날짜.plusDays(i)));
		}

		healthMetricRepository.saveAll(healthMetrics);
	}

	@Test
	void 건강지표를_성공적으로_조회한다() {
		//given

		//when
		HealthMetric 조회된_건강지표 = healthMetricSearchService.findByHealthMetricId(user.getOauthId(), 시작_날짜, CommonCode.STEP_COUNT);

		//then
		assertAll(
			() -> assertThat(조회된_건강지표.getUnit()).isEqualTo(조회할_건강지표.getUnit()),
			() -> assertThat(조회된_건강지표.getType()).isEqualTo(조회할_건강지표.getType()),
			() -> assertThat(조회된_건강지표.getUser().getId()).isEqualTo(조회할_건강지표.getUser().getId())
		);
	}

	@Test
	void 건강지표_조회실패로_예외를_발생한다() {
		//given
		LocalDate 등록_날짜 = LocalDate.of(2025, 1, 1);

		//when&then
		Assertions.assertThrows(HealthMetricNotFoundException.class, () -> {
			healthMetricSearchService.findByHealthMetricId(user.getOauthId(), 등록_날짜, CommonCode.STEP_COUNT);
		});

	}

	@Test
	void 최근_건강지표를_조회한다() {
		//given

		//when
		HealthMetric 조회된_건강지표 = healthMetricSearchService.findLastHealthMetricById(user.getOauthId(), CommonCode.STEP_COUNT);

		//then
		assertAll(
			() -> assertThat(조회된_건강지표.getUnit()).isEqualTo(최근_건강지표.getUnit()),
			() -> assertThat(조회된_건강지표.getType()).isEqualTo(최근_건강지표.getType()),
			() -> assertThat(조회된_건강지표.getUser().getId()).isEqualTo(최근_건강지표.getUser().getId())
		);
	}

	@Test
	void 최근_건강지표를_조회실패로_예외를_발생한다() {
		//given

		//when&then
		Assertions.assertThrows(HealthMetricNotFoundException.class, () -> {
			healthMetricSearchService.findLastHealthMetricById(user.getOauthId(), CommonCode.HEALTH);
		});
	}

	@Test
	void 기간내의_건강지표를_조회한다() {
		//given

		//when
		List<HealthMetric> 조회된_건강지표리스트 = healthMetricSearchService.findLastWeekHealthMetricById(user.getOauthId(), CommonCode.STEP_COUNT, 시작_날짜,
			시작_날짜.plusDays(6));
		var ind = 0;
		//then
		for (ind = 0; ind < 조회된_건강지표리스트.size(); ind++) {
			String unit = String.valueOf(10000 - ind * 1000);
			assertThat(조회된_건강지표리스트.get(ind).getUnit()).isEqualTo(unit);
			assertThat(조회된_건강지표리스트.get(ind).getType()).isEqualTo(조회할_건강지표.getType());
			assertThat(조회된_건강지표리스트.get(ind).getUser().getId()).isEqualTo(조회할_건강지표.getUser().getId());
			assertThat(조회된_건강지표리스트.get(ind).getCreatedAt()).isEqualTo(조회할_건강지표.getCreatedAt().plusDays(ind));
		}

	}

}
