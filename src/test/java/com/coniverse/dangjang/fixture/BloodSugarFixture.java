package com.coniverse.dangjang.fixture;

import java.time.LocalDate;

import com.coniverse.dangjang.domain.healthMetric.dto.request.HealthMetricPatchRequest;
import com.coniverse.dangjang.domain.healthMetric.dto.request.HealthMetricPostRequest;
import com.coniverse.dangjang.domain.healthMetric.dto.response.HealthMetricResponse;
import com.coniverse.dangjang.domain.healthMetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.healthMetric.enums.HealthMetricType;
import com.coniverse.dangjang.domain.user.entity.User;

/**
 * 혈당 fixture
 *
 * @author TEO
 * @since 1.0.0
 */
public class BloodSugarFixture {
	private static final LocalDate 생성일자 = LocalDate.of(2023, 12, 31);
	private static final String 등록_혈당_단위 = "140";
	private static final String 수정_혈당_단위 = "100";
	private static final HealthMetricType 등록_혈당_타입 = HealthMetricType.BEFORE_BREAKFAST;
	private static final HealthMetricType 수정_혈당_타입 = HealthMetricType.AFTER_LUNCH;

	public static HealthMetricPostRequest 혈당_등록_요청() {
		return new HealthMetricPostRequest(등록_혈당_타입.getTitle(), 등록_혈당_단위);
	}

	public static HealthMetricPatchRequest 타입_변경한_혈당_수정_요청() {
		return new HealthMetricPatchRequest(등록_혈당_타입.getTitle(), 수정_혈당_타입.getTitle(), 등록_혈당_단위);
	}

	public static HealthMetricPatchRequest 단위_변경한_혈당_수정_요청() {
		return new HealthMetricPatchRequest(등록_혈당_타입.getTitle(), null, 수정_혈당_단위);
	}

	public static HealthMetricResponse 혈당_등록_응답() {
		return new HealthMetricResponse(등록_혈당_타입, 생성일자, 등록_혈당_단위);
	}

	public static HealthMetric 정상_혈당(User user) {
		return HealthMetric.builder()
			.createdAt(생성일자)
			.healthMetricType(등록_혈당_타입)
			.user(user)
			.unit(등록_혈당_단위)
			.build();
	}
}
