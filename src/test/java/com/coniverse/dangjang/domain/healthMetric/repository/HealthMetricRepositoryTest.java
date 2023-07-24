package com.coniverse.dangjang.domain.healthMetric.repository;

import static com.coniverse.dangjang.fixture.BloodSugarFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

import com.coniverse.dangjang.domain.healthMetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.healthMetric.entity.HealthMetricId;
import com.coniverse.dangjang.domain.healthMetric.enums.HealthMetricCode;
import com.coniverse.dangjang.domain.healthMetric.enums.HealthMetricType;
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
	private User 기범;
	@Autowired
	private EntityManager em;

	@BeforeAll
	void setUp() {
		기범 = userRepository.save(유저_기범());
	}

	@Test
	void 건강지표를_성공적으로_저장한다() {
		// given
		HealthMetric 혈당 = 정상_혈당(기범);

		// when
		HealthMetricId 혈당_아이디 = healthMetricRepository.save(혈당).getHealthMetricId();

		// then
		HealthMetric 찾은_혈당 = healthMetricRepository.findByHealthMetricId(혈당_아이디).orElseThrow();
		assertAll(() -> assertThat(찾은_혈당.getOauthId()).isEqualTo(혈당.getOauthId()),
			() -> assertThat(찾은_혈당.getHealthMetricCode()).isEqualTo(혈당.getHealthMetricCode()),
			() -> assertThat(찾은_혈당.getHealthMetricType()).isEqualTo(혈당.getHealthMetricType()),
			() -> assertThat(찾은_혈당.getCreatedAt()).isEqualTo(혈당.getCreatedAt()),
			() -> assertThat(찾은_혈당.getUnit()).isEqualTo(혈당.getUnit()));
	}

	@Test
	void 같은_건강지표는_건강지표_아이디가_서로_같다() {
		// given & when
		HealthMetric 저장된_혈당 = healthMetricRepository.save(정상_혈당(기범));

		// then
		HealthMetric 찾은_혈당 = healthMetricRepository.findByHealthMetricId(저장된_혈당.getHealthMetricId()).orElseThrow();
		assertThat(찾은_혈당.getHealthMetricId()).isEqualTo(저장된_혈당.getHealthMetricId());
	}

	@Test
	void unit이_없는_건강지표를_저장할_경우_예외가_발생한다() {
		// given
		HealthMetric 혈당 = HealthMetric.builder()
			.healthMetricCode(HealthMetricCode.BLOOD_SUGAR)
			.healthMetricType(HealthMetricType.BEFORE_BREAKFAST)
			.createdAt(LocalDate.now())
			.user(기범)
			.build();

		// when
		healthMetricRepository.save(혈당);

		// then
		assertThatThrownBy(() -> em.flush())
			.isInstanceOf(ConstraintViolationException.class)
			.hasMessageContaining("unit");
	}
}
