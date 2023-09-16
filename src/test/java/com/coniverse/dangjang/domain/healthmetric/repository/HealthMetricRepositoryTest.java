package com.coniverse.dangjang.domain.healthmetric.repository;

import static com.coniverse.dangjang.fixture.HealthMetricFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.code.enums.GroupCode;
import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.repository.UserRepository;
import com.coniverse.dangjang.support.annotation.JpaRepositoryTest;

import jakarta.persistence.EntityManager;

/**
 * @author TEO, EVE
 * @since 1.0.0
 */
@JpaRepositoryTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HealthMetricRepositoryTest {
	@Autowired
	private HealthMetricRepository healthMetricRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EntityManager em;
	private String 테오_아이디;

	@BeforeAll
	void setUp() {
		테오_아이디 = userRepository.save(유저_테오()).getOauthId();
	}

	@Test
	void 건강지표를_성공적으로_저장한다() {
		// given
		User 테오 = userRepository.findById(테오_아이디).orElseThrow();

		// when
		HealthMetric 저장된_건강지표 = healthMetricRepository.save(건강지표_엔티티(테오));
		em.flush();

		// then
		HealthMetric 찾은_건강지표 = healthMetricRepository.findByHealthMetricId(저장된_건강지표.getOauthId(), 저장된_건강지표.getCreatedAt(), 저장된_건강지표.getType())
			.orElseThrow();

		assertAll(
			() -> assertThat(찾은_건강지표.getOauthId()).isEqualTo(저장된_건강지표.getOauthId()),
			() -> assertThat(찾은_건강지표.getType()).isEqualTo(저장된_건강지표.getType()),
			() -> assertThat(찾은_건강지표.getCreatedAt()).isEqualTo(저장된_건강지표.getCreatedAt()),
			() -> assertThat(찾은_건강지표.getUnit()).isEqualTo(저장된_건강지표.getUnit())
		);
	}

	@Test
	void unit이_없는_건강지표를_저장할_경우_예외가_발생한다() {
		// given
		User 테오 = userRepository.findById(테오_아이디).orElseThrow();
		HealthMetric unit이_없는_건강지표 = HealthMetric.builder()
			.type(CommonCode.BEFORE_BREAKFAST)
			.createdAt(LocalDate.now())
			.user(테오)
			.build();

		// when
		healthMetricRepository.save(unit이_없는_건강지표);

		// then
		assertThatThrownBy(() -> em.flush())
			.isInstanceOf(ConstraintViolationException.class)
			.hasMessageContaining("unit");
	}

	@Test
	void GroupCode로_일정한_기간_건강지표를_조회한다() {
		//given
		User 테오 = userRepository.findById(테오_아이디).orElseThrow();
		healthMetricRepository.saveAll(건강지표_엔티티_리스트(테오, CommonCode.AFTER_BREAKFAST, LocalDate.of(2020, 01, 01), 200, 10));
		//when
		List<HealthMetric> healthMetrics = healthMetricRepository.findLastWeekByGroupCodeAndCreatedAt(테오_아이디, GroupCode.BLOOD_SUGAR, LocalDate.of(2020, 01, 01),
			LocalDate.of(2020, 01, 07));
		//then
		assertThat(healthMetrics).hasSize(7);
	}
}
