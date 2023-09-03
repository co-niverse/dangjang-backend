package com.coniverse.dangjang.fixture;

import static com.coniverse.dangjang.fixture.HealthMetricFixture.*;

import com.coniverse.dangjang.domain.analysis.dto.AnalysisData;
import com.coniverse.dangjang.domain.analysis.dto.healthMetric.BloodSugarAnalysisData;
import com.coniverse.dangjang.domain.code.enums.CommonCode;

/**
 * 분석 데이터 fixture
 *
 * @author TEO
 * @since 1.0.0
 */
public class AnalysisDataFixture {
	public static AnalysisData 혈당_분석_데이터() {
		return new BloodSugarAnalysisData(건강지표_엔티티(CommonCode.BEFORE_BREAKFAST));
	}

	public static AnalysisData 운동_분석_데이터() {
		return new BloodSugarAnalysisData(건강지표_엔티티(CommonCode.STEP_COUNT));
	}

	public static AnalysisData 체중_분석_데이터() {
		return new BloodSugarAnalysisData(건강지표_엔티티(CommonCode.MEASUREMENT));
	}

	public static AnalysisData 당화혈색소_분석_데이터() {
		return new BloodSugarAnalysisData(건강지표_엔티티(CommonCode.HBA1C));
	}
}
