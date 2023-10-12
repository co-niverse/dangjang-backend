package com.coniverse.dangjang.fixture;

import static com.coniverse.dangjang.fixture.GuideFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthConnectPostRequest;
import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthConnectRegisterRequest;
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

	public static HealthConnectPostRequest 헬스_커넥트_데이터_등록_요청(String type, int count) {
		return new HealthConnectPostRequest(new HashSet<>(
			IntStream.range(0, count)
				.mapToObj(i -> new HealthMetricPostRequest(type, LocalDate.of(2023, 1, 1).plusDays(i).toString(), "140"))
				.toList())
		);
	}

	public static HealthConnectRegisterRequest 헬스_커넥트_연동_요청(boolean interlock) {
		return new HealthConnectRegisterRequest(interlock);
	}

	public static HealthMetricPatchRequest 타입_변경한_건강지표_수정_요청() {
		return new HealthMetricPatchRequest(등록_건강지표_상세명, 수정_건강지표_상세명, 생성일자, 등록_건강지표_단위);
	}

	public static HealthMetricPatchRequest 단위_변경한_건강지표_수정_요청() {
		return new HealthMetricPatchRequest(등록_건강지표_상세명, null, 생성일자, 수정_건강지표_단위);
	}

	public static HealthMetricResponse 건강지표_등록_응답() {
		return new HealthMetricResponse(등록_건강지표_상세명, LocalDate.parse(생성일자), 등록_건강지표_단위, 혈당_서브_가이드_응답());
	}

	public static HealthMetric 건강지표_엔티티(User user) {
		return HealthMetric.builder()
			.createdAt(LocalDate.parse(생성일자).plusDays(1))
			.type(등록_건강지표)
			.user(user)
			.unit(등록_건강지표_단위)
			.build();
	}

	public static HealthMetric 건강지표_엔티티(CommonCode type) {
		return HealthMetric.builder()
			.createdAt(LocalDate.parse(생성일자))
			.type(type)
			.user(유저_테오())
			.unit(등록_건강지표_단위)
			.build();
	}

	public static HealthMetric 건강지표_엔티티(String unit) {
		return HealthMetric.builder()
			.createdAt(LocalDate.parse(생성일자))
			.type(등록_건강지표)
			.user(유저_테오())
			.unit(unit)
			.build();
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

	public static HealthMetric 건강지표_엔티티(User user, CommonCode type, LocalDate createdAt) {
		return HealthMetric.builder()
			.createdAt(createdAt)
			.type(type)
			.user(user)
			.unit("1")
			.build();
	}

	public static HealthMetric 건강지표_엔티티(User user, CommonCode type, LocalDate createdAt, String unit) {
		return HealthMetric.builder()
			.createdAt(createdAt)
			.type(type)
			.user(user)
			.unit(unit)
			.build();
	}

	public static List<HealthMetric> 건강지표_엔티티_리스트(User user, CommonCode type, LocalDate createdAt, int unit, int needCount) {
		return Stream.iterate(0, i -> i + 1).limit(needCount)
			.map(n -> HealthMetric.builder()
				.createdAt(createdAt.plusDays(n))
				.type(type)
				.user(user)
				.unit(String.valueOf(unit + (n * 10)))
				.build())
			.collect(Collectors.toList());

	}

}
