package com.coniverse.dangjang.domain.healthmetric.mapper;

import static com.coniverse.dangjang.fixture.GuideFixture.*;
import static com.coniverse.dangjang.fixture.HealthMetricFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.coniverse.dangjang.domain.guide.common.dto.GuideResponse;
import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthMetricPatchRequest;
import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthMetricPostRequest;
import com.coniverse.dangjang.domain.healthmetric.dto.response.HealthMetricResponse;
import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;

/**
 * @author TEO
 * @since 1.0.0
 */
class HealthMetricMapperTest {
	HealthMetricMapper healthMetricMapper = new HealthMetricMapperImpl();

	@Test
	void 건강지표_entity를_response_dto로_변환한다() {
		// given
		HealthMetric 건강지표 = 건강지표_엔티티(유저_테오());
		GuideResponse 가이드_응답 = 혈당_서브_가이드_응답();

		// when
		HealthMetricResponse 건강지표_응답 = healthMetricMapper.toResponse(건강지표, 가이드_응답);

		// then
		assertAll(
			() -> assertThat(건강지표_응답.createdAt()).isEqualTo(건강지표.getCreatedAt()),
			() -> assertThat(건강지표_응답.type()).isEqualTo(건강지표.getType().getTitle()),
			() -> assertThat(건강지표_응답.unit()).isEqualTo(건강지표.getUnit()),
			() -> assertThat(건강지표_응답.guide()).isEqualTo(가이드_응답)
		);
	}

	@Test
	void post_resquest_dto를_건강지표_entity로_변환한다() {
		// given
		HealthMetricPostRequest 건강지표_등록_요청 = 건강지표_등록_요청();

		// when
		HealthMetric 건강지표 = healthMetricMapper.toEntity(건강지표_등록_요청, 유저_테오());

		// then
		assertAll(
			() -> assertThat(건강지표_등록_요청.type()).isEqualTo(건강지표.getType().getTitle()),
			() -> assertThat(건강지표_등록_요청.createdAt()).isEqualTo(건강지표.getCreatedAt().toString()),
			() -> assertThat(건강지표_등록_요청.unit()).isEqualTo(건강지표.getUnit())
		);
	}

	@Test
	void patch_resquest_dto를_건강지표_entity로_변환한다() {
		// given
		HealthMetricPatchRequest 건강지표_수정_요청 = 타입_변경한_건강지표_수정_요청();

		// when
		HealthMetric 건강지표 = healthMetricMapper.toEntity(건강지표_수정_요청, 유저_테오());

		// then
		assertAll(
			() -> assertThat(건강지표_수정_요청.newType()).isEqualTo(건강지표.getType().getTitle()),
			() -> assertThat(건강지표_수정_요청.createdAt()).isEqualTo(건강지표.getCreatedAt().toString()),
			() -> assertThat(건강지표_수정_요청.unit()).isEqualTo(건강지표.getUnit())
		);
	}
}
