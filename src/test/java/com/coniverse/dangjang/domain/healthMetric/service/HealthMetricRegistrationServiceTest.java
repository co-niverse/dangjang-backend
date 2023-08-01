package com.coniverse.dangjang.domain.healthMetric.service;

import static com.coniverse.dangjang.fixture.BloodSugarFixture.*;
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

import com.coniverse.dangjang.domain.healthMetric.dto.request.HealthMetricPatchRequest;
import com.coniverse.dangjang.domain.healthMetric.dto.request.HealthMetricPostRequest;
import com.coniverse.dangjang.domain.healthMetric.dto.response.HealthMetricResponse;
import com.coniverse.dangjang.domain.healthMetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.healthMetric.repository.HealthMetricRepository;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.repository.UserRepository;

/**
 * @author TEO
 * @since 1.0.0
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HealthMetricRegistrationServiceTest {
	private final LocalDate 등록_일자 = LocalDate.of(2023, 12, 31);
	@Autowired
	private HealthMetricRegistrationService healthMetricRegistrationService;
	@Autowired
	private HealthMetricRepository healthMetricRepository;
	@Autowired
	private UserRepository userRepository;
	private User 테오;
	private HealthMetric 등록된_혈당;

	@BeforeAll
	void setUp() {
		테오 = userRepository.save(유저_테오());
	}

	@AfterAll
	void tearDown() {
		healthMetricRepository.deleteAll();
		userRepository.deleteAll();
	}

	@Order(50)
	@Test
	void 혈당을_성공적으로_등록한다() {
		// given
		HealthMetricPostRequest request = 혈당_등록_요청();

		// when
		HealthMetricResponse response = healthMetricRegistrationService.register(request, 등록_일자, 테오.getOauthId());

		// then
		등록된_혈당 = healthMetricRepository.findByHealthMetricId(테오.getOauthId(), response.createdAt(), response.healthMetricType())
			.orElseThrow();

		assertAll(
			() -> assertThat(등록된_혈당.getHealthMetricType().getTitle()).isEqualTo(request.healthMetricType()),
			() -> assertThat(등록된_혈당.getUnit()).isEqualTo(request.unit()),
			() -> assertThat(등록된_혈당.getCreatedAt()).isEqualTo(등록_일자),
			() -> assertThat(등록된_혈당.getOauthId()).isEqualTo(테오.getOauthId())
		);
	}

	@Order(100)
	@Test
	void 단위만_변경된_혈당을_성공적으로_수정한다() {
		// given
		HealthMetricPatchRequest request = 단위_변경한_혈당_수정_요청();

		// when
		HealthMetricResponse response = healthMetricRegistrationService.update(request, 등록_일자, 테오.getOauthId());

		// then

		HealthMetric 수정된_혈당 = healthMetricRepository.findByHealthMetricId(테오.getOauthId(), response.createdAt(), response.healthMetricType())
			.orElseThrow();

		assertAll(
			() -> assertThat(수정된_혈당.getUnit()).isEqualTo(request.unit()),
			() -> assertThat(수정된_혈당.getUnit()).isNotEqualTo(등록된_혈당.getUnit()),
			() -> assertThat(수정된_혈당.getHealthMetricType()).isEqualTo(등록된_혈당.getHealthMetricType())
		);
	}

	@Order(200)
	@Test
	void 타입이_변경된_혈당을_성공적으로_수정한다() {
		// given
		HealthMetricPatchRequest request = 타입_변경한_혈당_수정_요청();

		// when
		HealthMetricResponse response = healthMetricRegistrationService.update(request, 등록_일자, 테오.getOauthId());

		// then
		HealthMetric 수정된_혈당 = healthMetricRepository.findByHealthMetricId(테오.getOauthId(), response.createdAt(), response.healthMetricType())
			.orElseThrow();

		assertAll(
			() -> assertThat(수정된_혈당.getHealthMetricType().getTitle()).isEqualTo(request.newHealthMetricType()),
			() -> assertThat(수정된_혈당.getUnit()).isEqualTo(request.unit()),
			() -> assertThat(수정된_혈당.getHealthMetricType()).isNotEqualTo(등록된_혈당.getHealthMetricType()),
			() -> assertThat(수정된_혈당.getUnit()).isEqualTo(등록된_혈당.getUnit())
		);
	}
}
