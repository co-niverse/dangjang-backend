package com.coniverse.dangjang.fixture;

import java.time.LocalDate;

import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthMetricPatchRequest;
import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthMetricPostRequest;
import com.coniverse.dangjang.domain.healthmetric.dto.response.HealthMetricResponse;
import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.user.entity.User;

/**
 * 건강지표 fixture
 *
 * @author TEO
 * @since 1.0.0
 */
public class HealthMetricFixture {
	private static final LocalDate 생성일자 = LocalDate.of(2023, 12, 31);
	private static final String 등록_건강지표_단위 = "140";
	private static final String 수정_건강지표_단위 = "100";
	private static final CommonCode 등록_건강지표 = CommonCode.BS_BBF;
	private static final String 등록_건강지표_상세명 = 등록_건강지표.getTitle();
	private static final String 수정_건강지표_상세명 = CommonCode.BS_ALC.getTitle();

	public static HealthMetricPostRequest 건강지표_등록_요청(CommonCode commonCode, String unit) {
		String 건강지표_상세명 = commonCode.getTitle();
		return new HealthMetricPostRequest(건강지표_상세명, unit);
	}

	public static HealthMetricPatchRequest 타입_변경한_건강지표_수정_요청() {
		return new HealthMetricPatchRequest(등록_건강지표_상세명, 수정_건강지표_상세명, 등록_건강지표_단위);
	}

	public static HealthMetricPatchRequest 단위_변경한_건강지표_수정_요청() {
		return new HealthMetricPatchRequest(등록_건강지표_상세명, null, 수정_건강지표_단위);
	}

	public static HealthMetricResponse 건강지표_등록_응답() {
		return new HealthMetricResponse(등록_건강지표_상세명, 생성일자, 등록_건강지표_단위);
	}

	public static HealthMetric 건강지표_엔티티(User user) {
		return HealthMetric.builder()
			.createdAt(생성일자)
			.commonCode(등록_건강지표)
			.user(user)
			.unit(등록_건강지표_단위)
			.build();
	}
}
