package com.coniverse.dangjang.domain.healthmetric.repository;

import static com.coniverse.dangjang.fixture.BloodSugarFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.healthmetric.enums.HealthMetricType;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.repository.UserRepository;
import com.coniverse.dangjang.support.annotation.RepositoryTest;

import jakarta.persistence.EntityManager;

/**
 * @author TEO
 * @since 1.0.0
 */
@RepositoryTest
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

	@AfterAll
	void tearDown() {
		healthMetricRepository.deleteAll();
		userRepository.deleteAll();
	}

	@Test
	void 건강지표를_성공적으로_저장한다() {
		// given
		User 테오 = userRepository.findById(테오_아이디).orElseThrow();

		// when
		HealthMetric 저장된_혈당 = healthMetricRepository.save(정상_혈당(테오));
		em.flush();

		// then
		HealthMetric 찾은_혈당 = healthMetricRepository.findByHealthMetricId(저장된_혈당.getOauthId(), 저장된_혈당.getCreatedAt(), 저장된_혈당.getHealthMetricType())
			.orElseThrow();

		assertAll(
			() -> assertThat(찾은_혈당.getOauthId()).isEqualTo(저장된_혈당.getOauthId()),
			() -> assertThat(찾은_혈당.getHealthMetricType()).isEqualTo(저장된_혈당.getHealthMetricType()),
			() -> assertThat(찾은_혈당.getCreatedAt()).isEqualTo(저장된_혈당.getCreatedAt()),
			() -> assertThat(찾은_혈당.getUnit()).isEqualTo(저장된_혈당.getUnit())
		);
	}

	@Test
	void unit이_없는_건강지표를_저장할_경우_예외가_발생한다() {
		// given
		User 테오 = userRepository.findById(테오_아이디).orElseThrow();
		HealthMetric unit이_없는_혈당 = HealthMetric.builder()
			.healthMetricType(HealthMetricType.BEFORE_BREAKFAST)
			.createdAt(LocalDate.now())
			.user(테오)
			.build();

		// when
		healthMetricRepository.save(unit이_없는_혈당);

		// then
		assertThatThrownBy(() -> em.flush())
			.isInstanceOf(ConstraintViolationException.class)
			.hasMessageContaining("unit");
	}
}