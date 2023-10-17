package com.coniverse.dangjang.domain.healthmetric.service;

import static com.coniverse.dangjang.fixture.HealthMetricFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.healthmetric.dto.HealthMetricLastDateResponse;
import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.healthmetric.exception.HealthMetricNotFoundException;
import com.coniverse.dangjang.domain.healthmetric.repository.HealthMetricRepository;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.repository.UserRepository;

/**
 * @author EVE, TEO
 * @since 1.0.0
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class HealthMetricSearchServiceTest {
	private final CommonCode 조회_타입 = CommonCode.STEP_COUNT;
	private final LocalDate 조회_날짜 = LocalDate.of(2021, 8, 1);
	@Autowired
	private HealthMetricRepository healthMetricRepository;
	@Autowired
	private HealthMetricSearchService healthMetricSearchService;
	@Autowired
	private UserRepository userRepository;
	private String oauthId;
	private LocalDate 마지막_생성일 = 조회_날짜.plusDays(10);

	@BeforeEach
	void setUpUser() {
		User user = userRepository.save(유저_테오());
		oauthId = user.getOauthId();
		healthMetricRepository.saveAll(
			IntStream.range(0, 10)
				.mapToObj(i -> 건강지표_엔티티(user, 조회_타입, 조회_날짜.plusDays(i)))
				.toList()
		);
		healthMetricRepository.save(건강지표_엔티티(user, 조회_타입, 마지막_생성일));
	}

	@Test
	void 건강지표를_성공적으로_조회한다() {
		// when
		HealthMetric 조회된_건강지표 = healthMetricSearchService.findByHealthMetricId(oauthId, 조회_날짜, 조회_타입);

		// then
		assertAll(
			() -> assertThat(조회된_건강지표.getType()).isEqualTo(조회_타입),
			() -> assertThat(조회된_건강지표.getCreatedAt()).isEqualTo(조회_날짜),
			() -> assertThat(조회된_건강지표.getOauthId()).isEqualTo(oauthId)
		);
	}

	@Test
	void 해당_날짜에_존재하지_않는_건강지표를_조회하면_예외를_발생한다() {
		// given
		LocalDate 없는_날짜 = LocalDate.of(2025, 1, 1);

		// when & then
		assertThatThrownBy(() -> healthMetricSearchService.findByHealthMetricId(oauthId, 없는_날짜, 조회_타입))
			.isInstanceOf(HealthMetricNotFoundException.class);
	}

	@Test
	void 타입이_존재하지_않는_건강지표를_조회하면_예외를_발생한다() {
		// given
		CommonCode 없는_타입 = CommonCode.HEALTH;

		// when & then
		assertThatThrownBy(() -> healthMetricSearchService.findByHealthMetricId(oauthId, 조회_날짜, 없는_타입))
			.isInstanceOf(HealthMetricNotFoundException.class);
	}

	@Test
	void 최근_날짜의_건강지표_타입을_조회한다() {
		// when
		HealthMetric 조회된_건강지표 = healthMetricSearchService.findLastHealthMetricById(oauthId, 조회_타입);

		// then
		assertAll(
			() -> assertThat(조회된_건강지표.getType()).isEqualTo(조회_타입),
			() -> assertThat(조회된_건강지표.getCreatedAt()).isEqualTo(마지막_생성일),
			() -> assertThat(조회된_건강지표.getOauthId()).isEqualTo(oauthId)
		);
	}

	@Test
	void 존재하지_않는_건강지표의_최근_날짜를_조회하면_예외를_발생한다() {
		// given
		CommonCode 없는_타입 = CommonCode.HEALTH;

		// when & then
		assertThatThrownBy(() -> healthMetricSearchService.findLastHealthMetricById(oauthId, 없는_타입))
			.isInstanceOf(HealthMetricNotFoundException.class);
	}

	@Test
	void 기간내의_건강지표를_조회한다() {
		// given
		LocalDate 시작_날짜 = 조회_날짜.plusDays(1);
		LocalDate 끝_날짜 = 조회_날짜.plusDays(6);

		// when
		List<HealthMetric> 조회된_건강지표_리스트 = healthMetricSearchService.findWeeklyHealthMetricById(oauthId, 조회_타입, 시작_날짜, 끝_날짜);

		// then
		assertThat(조회된_건강지표_리스트).hasSize(6);
		assertTrue(
			IntStream.range(1, 6)
				.allMatch(i -> 조회된_건강지표_리스트.get(i - 1).getType().equals(조회_타입)
					&& 조회된_건강지표_리스트.get(i - 1).getOauthId().equals(oauthId)
					&& 조회된_건강지표_리스트.get(i - 1).getCreatedAt().equals(조회_날짜.plusDays(i)))
		);
	}

	@Test
	void 건강지표_마지막_생성일을_조회한다() {
		// when
		HealthMetricLastDateResponse 조회된_마지막_생성일 = healthMetricSearchService.findHealthMetricLastDate(oauthId);

		// then
		assertThat(조회된_마지막_생성일.date()).isEqualTo(마지막_생성일);
	}

}
