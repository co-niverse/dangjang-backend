package com.coniverse.dangjang.fixture;

import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.junit.jupiter.params.provider.Arguments.*;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import com.coniverse.dangjang.domain.analysis.vo.AnalysisData.WeightAnalysisData;
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
	private static final String 체중_등록_건강지표_단위 = "50"; //TODO 건강지표 등록 요청 메서드 merge 되면 삭제하기
	private static final String 체중_등록_건강지표_상세명 = "체중"; //TODO 건강지표 등록 요청 메서드 merge 되면 삭제하기

	public static HealthMetricPostRequest 건강지표_등록_요청() {
		return new HealthMetricPostRequest(등록_건강지표_상세명, 등록_건강지표_단위);
	}

	//TODO 건강지표 등록 요청 메서드 merge 되면 삭제하기
	public static HealthMetricPostRequest 체중_건강지표_등록_요청() {
		return new HealthMetricPostRequest(체중_등록_건강지표_상세명, 체중_등록_건강지표_단위);
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

	public static HealthMetric 건강지표_더미데이터(User user, CommonCode commonCode, String unit) {
		return HealthMetric.builder()
			.createdAt(생성일자)
			.commonCode(commonCode)
			.user(user)
			.unit(unit)
			.build();
	}

	public static Stream<Arguments> 체중_건강지표_목록() {
		return Stream.of(arguments(CommonCode.WT_MEM, "30"), arguments(CommonCode.WT_MEM, "50"), arguments(CommonCode.WT_MEM, "100"),
			arguments(CommonCode.WT_MEM, "120"), arguments(CommonCode.WT_MEM, "150"));
	}

	public static WeightAnalysisData 체중분석_데이터(CommonCode commonCode, String unit) {
		return new WeightAnalysisData(건강지표_더미데이터(유저_테오(), commonCode, unit), 유저_테오());

	}
}
