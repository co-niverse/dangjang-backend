package com.coniverse.dangjang.domain.healthmetric.mapper;

import static com.coniverse.dangjang.fixture.HealthMetricFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthMetricPatchRequest;
import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthMetricPostRequest;
import com.coniverse.dangjang.domain.healthmetric.dto.response.HealthMetricResponse;
import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;

class HealthMetricMapperTest {
	public static final LocalDate 등록_일자 = LocalDate.of(2023, 12, 31);
	HealthMetricMapper healthMetricMapper = new HealthMetricMapperImpl();
	
	@Test
	void 건강지표_entity를_response_dto로_변환한다() {
		// given
		HealthMetric 건강지표 = 건강지표_엔티티(유저_테오());

		// when
		HealthMetricResponse 건강지표_응답 = healthMetricMapper.toResponse(건강지표);

		// then
		assertAll(
			() -> assertThat(건강지표_응답.createdAt()).isEqualTo(건강지표.getCreatedAt()),
			() -> assertThat(건강지표_응답.title()).isEqualTo(건강지표.getCommonCode().getTitle()),
			() -> assertThat(건강지표_응답.unit()).isEqualTo(건강지표.getUnit())
		);
	}

	@Test
	void post_resquest_dto를_건강지표_entity로_변환한다() {
		// given
		HealthMetricPostRequest 건강지표_등록_요청 = 건강지표_등록_요청();

		// when
		HealthMetric 건강지표 = healthMetricMapper.toEntity(건강지표_등록_요청, 등록_일자, 유저_테오());

		// then
		assertAll(
			() -> assertThat(건강지표_등록_요청.title()).isEqualTo(건강지표.getCommonCode().getTitle()),
			() -> assertThat(건강지표_등록_요청.unit()).isEqualTo(건강지표.getUnit())
		);
	}

	@Test
	void patch_resquest_dto를_건강지표_entity로_변환한다() {
		// given
		HealthMetricPatchRequest 건강지표_수정_요청 = 타입_변경한_건강지표_수정_요청();

		// when
		HealthMetric 건강지표 = healthMetricMapper.toEntity(건강지표_수정_요청, 등록_일자, 유저_테오());

		// then
		assertAll(
			() -> assertThat(건강지표_수정_요청.newTitle()).isEqualTo(건강지표.getCommonCode().getTitle()),
			() -> assertThat(건강지표_수정_요청.unit()).isEqualTo(건강지표.getUnit())
		);
	}
}
