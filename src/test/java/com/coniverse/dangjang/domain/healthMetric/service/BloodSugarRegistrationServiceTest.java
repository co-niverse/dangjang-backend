package com.coniverse.dangjang.domain.healthMetric.service;

import static com.coniverse.dangjang.fixture.BloodSugarFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
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
import com.coniverse.dangjang.domain.healthMetric.entity.HealthMetricId;
import com.coniverse.dangjang.domain.healthMetric.repository.HealthMetricRepository;
import com.coniverse.dangjang.domain.healthMetric.service.bloodSugar.BloodSugarRegistrationService;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.repository.UserRepository;

/**
 * @author TEO
 * @since 1.0.0
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Disabled
class BloodSugarRegistrationServiceTest {
	@Autowired
	private BloodSugarRegistrationService bloodSugarRegistrationService;
	@Autowired
	private HealthMetricRepository healthMetricRepository;
	@Autowired
	private UserRepository userRepository;
	private User 기범;
	private HealthMetric 수정할_혈당;

	@BeforeAll
	void setUp() {
		기범 = userRepository.save(유저_기범());
		수정할_혈당 = healthMetricRepository.save(정상_혈당(기범));
	}

	@Test
	void 혈당을_성공적으로_등록한다() {
		// given
		HealthMetricPostRequest bloodSugarPostRequest = 혈당_등록_요청();
		LocalDate createdAt = LocalDate.of(2023, 12, 30);
		// when
		HealthMetricResponse bloodSugarResponse = bloodSugarRegistrationService.register(bloodSugarPostRequest, createdAt);

		// then
		HealthMetricId id = generateHealthMetricId(bloodSugarResponse);
		HealthMetric healthMetric = healthMetricRepository.findByHealthMetricId(id).orElseThrow();

		assertAll(
			() -> assertThat(healthMetric.getHealthMetricType().getTitle()).isEqualTo(bloodSugarPostRequest.healthMetricType()),
			() -> assertThat(healthMetric.getUnit()).isEqualTo(bloodSugarPostRequest.unit()),
			() -> assertThat(healthMetric.getOauthId()).isEqualTo(기범.getOauthId())
		);
	}

	@Order(100)
	@Test
	void 단위만_변경된_혈당을_성공적으로_수정한다() {
		// given
		String healthMetricType = 수정할_혈당.getHealthMetricType().getTitle();
		HealthMetricPatchRequest bloodSugarPatchRequest = new HealthMetricPatchRequest(healthMetricType, healthMetricType, "100");

		// when
		HealthMetricResponse bloodSugarPatchResponse = bloodSugarRegistrationService.update(bloodSugarPatchRequest, 수정할_혈당.getCreatedAt());

		// then
		HealthMetricId id = generateHealthMetricId(bloodSugarPatchResponse);
		HealthMetric healthMetric = healthMetricRepository.findByHealthMetricId(id).orElseThrow();

		assertAll(
			() -> assertThat(healthMetric.getUnit()).isEqualTo(bloodSugarPatchRequest.unit()),
			() -> assertThat(healthMetric.getUnit()).isNotEqualTo(수정할_혈당.getUnit())
		);
	}

	@Order(200)
	@Test
	void 타입이_변경된_혈당을_성공적으로_수정한다() {
		// given
		HealthMetricPatchRequest bloodSugarPatchRequest = 혈당_수정_요청();

		// when
		HealthMetricResponse bloodSugarPatchResponse = bloodSugarRegistrationService.update(bloodSugarPatchRequest, 수정할_혈당.getCreatedAt());

		// then
		HealthMetricId id = generateHealthMetricId(bloodSugarPatchResponse);
		HealthMetric healthMetric = healthMetricRepository.findByHealthMetricId(id).orElseThrow();

		assertAll(
			() -> assertThat(healthMetric.getHealthMetricType().getTitle()).isEqualTo(bloodSugarPatchRequest.curHealthMetricType()),
			() -> assertThat(healthMetric.getUnit()).isEqualTo(bloodSugarPatchRequest.unit()),
			() -> assertThat(healthMetric.getHealthMetricType()).isNotEqualTo(수정할_혈당.getHealthMetricType()),
			() -> assertThat(healthMetric.getUnit()).isNotEqualTo(수정할_혈당.getUnit())
		);
	}

	private HealthMetricId generateHealthMetricId(HealthMetricResponse healthMetricResponse) {
		return HealthMetricId.builder()
			.createdAt(healthMetricResponse.createdAt())
			.healthMetricCode(healthMetricResponse.healthMetricCode())
			.healthMetricType(healthMetricResponse.healthMetricType())
			.oauthId(기범.getOauthId())
			.build();
	}
}
