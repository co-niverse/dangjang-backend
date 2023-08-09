package com.coniverse.dangjang.domain.analysis.vo.analysisdata;

import java.time.LocalDate;

import com.coniverse.dangjang.domain.analysis.exception.NonAnalyticDataException;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.code.enums.GroupCode;
import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 분석 데이터 추상 클래스
 *
 * @author TEO
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public abstract class HealthMetricAnalysisData {
	private LocalDate createdAt;
	private CommonCode commonCode;
	private String oauthId;

	/**
	 * 건강지표를 분석 데이터로 변환한다.
	 *
	 * @param healthMetric 건강지표
	 * @param user         건강지표 등록 유저
	 * @return 분석 데이터 vo
	 * @since 1.0.0
	 */
	public static HealthMetricAnalysisData of(HealthMetric healthMetric, User user) {
		GroupCode groupCode = GroupCode.findByCode(healthMetric.getType());
		if (groupCode.equals(GroupCode.BLOOD_SUGAR)) {
			return new BloodSugarAnalysisData(healthMetric, user);
		}
		throw new NonAnalyticDataException();
	}

	/**
	 * 각 분석 데이터마다 단위의 타입이 다르기 때문에 타입을 변환한다
	 *
	 * @param unit 변환할 단위
	 * @since 1.0.0
	 */
	abstract void convertUnit(String unit);
}
