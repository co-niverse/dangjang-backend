package com.coniverse.dangjang.domain.healthmetric.mapper;

import static com.coniverse.dangjang.fixture.BloodSugarFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.coniverse.dangjang.domain.healthmetric.dto.response.HealthMetricResponse;
import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;

public class HealthMetricMapperTest {
	HealthMetricMapper healthMetricMapper = new HealthMetricMapperImpl();

	@Test
	void 건강지표_entity를_response_dto로_변환한다() {
		// given
		HealthMetric 혈당 = 정상_혈당(유저_테오());

		// when
		HealthMetricResponse 혈당_응답 = healthMetricMapper.toResponse(혈당);

		// then
		assertAll(
			() -> assertThat(혈당_응답.createdAt()).isEqualTo(혈당.getCreatedAt()),
			() -> assertThat(혈당_응답.healthMetricType()).isEqualTo(혈당.getHealthMetricType()),
			() -> assertThat(혈당_응답.unit()).isEqualTo(혈당.getUnit())
		);
	}

	@Test
	void post_resquest_dto를_건강지표_entity로_변환한다() {

	}

	@Test
	void patch_resquest_dto를_건강지표_entity로_변환한다() {

	}
}
