package com.coniverse.dangjang.domain.analysis.strategy;

import org.springframework.stereotype.Component;

import com.coniverse.dangjang.domain.analysis.dto.AnalysisData;
import com.coniverse.dangjang.domain.analysis.dto.healthMetric.WeightAnalysisData;
import com.coniverse.dangjang.domain.analysis.enums.Alert;
import com.coniverse.dangjang.domain.analysis.enums.BmiAlert;
import com.coniverse.dangjang.domain.code.enums.GroupCode;
import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.user.entity.enums.Gender;

/**
 * 체중 분석 전략
 *
 * @author EVE
 * @see WeightAnalysisData
 * @since 1.0.0
 */
@Component
public class WeightAnalysisStrategy implements AnalysisStrategy {
	@Override
	public AnalysisData createAnalysisData(HealthMetric healthMetric) {
		return new WeightAnalysisData(healthMetric);
	}

	/**
	 * 체중 분석 데이터를 생성한다.
	 *
	 * @param analysisData 건강지표
	 * @return 체중 분석 데이터
	 * @since 1.0.0
	 */
	@Override
	public AnalysisData analyze(AnalysisData analysisData) {
		WeightAnalysisData data = (WeightAnalysisData)analysisData;

		double bmi = calculateBmi(data.getHeight(), data.getUnit());
		Alert alert = BmiAlert.findAlertByBmi(bmi);
		int weightDiff = this.calculateWeightDiff(data.getHeight(), data.getUnit(), data.getGender());
		data.setBmi(bmi);
		data.setAlert(alert);
		data.setWeightDiff(weightDiff);
		return data;
	}

	@Override
	public GroupCode getGroupCode() {
		return GroupCode.WEIGHT;
	}

	/**
	 * BMI 수치를 계산하여 소수점 둘째 자리까지 반올림한다.
	 * <p>
	 * BMI = 몸무게(kg) / 키(m)의 제곱
	 *
	 * @param height 키
	 * @param weight 몸무게
	 * @return bmi
	 * @since 1.0.0
	 */
	private double calculateBmi(int height, int weight) {
		double bmi = weight / Math.pow(height / 100.0, 2);
		return Math.round(bmi * 100) / 100.0;
	}

	/**
	 * 표준 체중 대비 편차를 계산한다.
	 * <p>
	 * 표준 체중 = 키(m)의 제곱 * 표준 체중 비율
	 *
	 * @param height 키
	 * @param weight 몸무게
	 * @param gender 성별
	 * @return 정상 체중 대비 편차
	 * @since 1.0.0
	 */
	private int calculateWeightDiff(int height, int weight, Gender gender) {
		int standardWeight = (int)(Math.pow(height / 100.0, 2) * gender.getStandardWeightRatio());
		return weight - standardWeight;
	}
}
