package com.coniverse.dangjang.domain.healthmetric.mapper;

import static com.coniverse.dangjang.fixture.HealthMetricFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.*;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthMetricPatchRequest;
import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthMetricPostRequest;
import com.coniverse.dangjang.domain.healthmetric.dto.response.HealthMetricResponse;
import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;

class HealthMetricMapperTest {
	public static final LocalDate 등록_일자 = LocalDate.of(2023, 12, 31);
	HealthMetricMapper healthMetricMapper = new HealthMetricMapperImpl();

	static Stream<Arguments> 건강지표_목록() {
		return Stream.of(arguments(CommonCode.WT_MEM, "50"), arguments(CommonCode.BS_BBF, "140"));
	}

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

	@ParameterizedTest
	@MethodSource("건강지표_목록")
	void post_resquest_dto를_건강지표_entity로_변환한다(CommonCode commonCode, String unit) {
		// given
		HealthMetricPostRequest 건강지표_등록_요청 = 건강지표_등록_요청(commonCode, unit);

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
