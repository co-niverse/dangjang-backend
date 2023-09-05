package com.coniverse.dangjang.fixture;

import static com.coniverse.dangjang.fixture.GuideFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;

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
	private static final String 생성일자 = "2023-12-31";
	private static final String 등록_건강지표_단위 = "140";
	private static final String 수정_건강지표_단위 = "100";
	private static final CommonCode 등록_건강지표 = CommonCode.BEFORE_BREAKFAST;
	private static final String 등록_건강지표_상세명 = 등록_건강지표.getTitle();
	private static final String 수정_건강지표_상세명 = CommonCode.AFTER_LUNCH.getTitle();
	private static final String 가이드_아이디 = "12341234";

	public static HealthMetricPostRequest 건강지표_등록_요청() {
		return new HealthMetricPostRequest(등록_건강지표_상세명, 생성일자, 등록_건강지표_단위);
	}

	public static HealthMetricPatchRequest 타입_변경한_건강지표_수정_요청() {
		return new HealthMetricPatchRequest(등록_건강지표_상세명, 수정_건강지표_상세명, 생성일자, 등록_건강지표_단위);
	}

	public static HealthMetricPatchRequest 단위_변경한_건강지표_수정_요청() {
		return new HealthMetricPatchRequest(등록_건강지표_상세명, null, 생성일자, 수정_건강지표_단위);
	}

	public static HealthMetricResponse 건강지표_등록_응답() {
		return new HealthMetricResponse(등록_건강지표_상세명, LocalDate.parse(생성일자), 등록_건강지표_단위, 혈당_가이드_응답());
	}

	public static HealthMetric 건강지표_엔티티(User user) {
		HealthMetric healthMetric = HealthMetric.builder()
			.createdAt(LocalDate.parse(생성일자))
			.type(등록_건강지표)
			.user(user)
			.unit(등록_건강지표_단위)
			.build();
		healthMetric.updateGuideId(가이드_아이디);
		return healthMetric;
	}

	public static HealthMetric 건강지표_엔티티(CommonCode type) {
		HealthMetric healthMetric = HealthMetric.builder()
			.createdAt(LocalDate.parse(생성일자))
			.type(type)
			.user(유저_테오())
			.unit(등록_건강지표_단위)
			.build();
		healthMetric.updateGuideId(가이드_아이디);
		return healthMetric;
	}

	public static HealthMetric 건강지표_엔티티(String unit) {
		HealthMetric healthMetric = HealthMetric.builder()
			.createdAt(LocalDate.parse(생성일자))
			.type(등록_건강지표)
			.user(유저_테오())
			.unit(unit)
			.build();
		healthMetric.updateGuideId(가이드_아이디);
		return healthMetric;
	}

	/**
	 * 분석 데이터 fixture에서 사용
	 */
	public static HealthMetric 건강지표_엔티티(CommonCode type, String unit) {
		return HealthMetric.builder()
			.createdAt(LocalDate.parse(생성일자))
			.type(type)
			.user(유저_테오())
			.unit(unit)
			.build();
	}

	public static HealthMetric 건강지표_엔티티(User user, CommonCode type, String unit) {
		return HealthMetric.builder()
			.createdAt(LocalDate.parse(생성일자))
			.type(type)
			.user(user)
			.unit(unit)
			.build();
	}

	public static HealthMetric 건강지표_엔티티(User user, CommonCode type, String unit, String guideId) {
		HealthMetric healthMetric = HealthMetric.builder()
			.createdAt(LocalDate.parse(생성일자))
			.type(type)
			.user(user)
			.unit(unit)
			.build();
		healthMetric.updateGuideId(guideId);
		return healthMetric;
	}
}
