package com.coniverse.dangjang.domain.analysis.strategy;

import org.springframework.stereotype.Component;

import com.coniverse.dangjang.domain.analysis.dto.AnalysisData;
import com.coniverse.dangjang.domain.analysis.dto.healthMetric.WeightAnalysisData;
import com.coniverse.dangjang.domain.analysis.enums.Alert;
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
		data.setBmi(calculateBmi(data.getHeight(), data.getUnit()));
		Alert alert = this.findAlert(data.getBmi());
		int weightDiff = this.calculateWeightDiff(data.getHeight(), data.getUnit(), data.getGender());
		data.setAlert(alert);
		data.setWeightDiff(weightDiff);
		return data;
	}

	@Override
	public GroupCode getGroupCode() {
		return GroupCode.WEIGHT;
	}

	/**
	 * BMI 수치에 따라 경보를 찾는다.
	 *
	 * @param bmi BMI 수치
	 * @return 경보
	 * @since 1.0.0
	 */
	public Alert findAlert(double bmi) {
		if (bmi < 18.5) {
			return Alert.LOW_WEIGHT;
		} else if (bmi < 22.9) {
			return Alert.NORMAL_WEIGHT;
		} else if (bmi < 24.9) {
			return Alert.OVERWEIGHT;
		} else if (bmi < 29.9) {
			return Alert.LEVEL_1_OBESITY;
		} else if (bmi < 34.9) {
			return Alert.LEVEL_2_OBESITY;
		}
		return Alert.LEVEL_3_OBESITY;
	}

	/**
	 * BMI 수치를 계산한다.
	 *
	 * @param height 키
	 * @param weight 몸무게
	 * @return bmi
	 * @since 1.0.0
	 */
	public double calculateBmi(int height, int weight) {
		return weight / Math.pow(height / 100.0, 2.0);
	}

	/**
	 * 정상 체중 대비 편차를 계산한다.
	 *
	 * @param height 키
	 * @param weight 몸무게
	 * @param gender 성별
	 * @return 정상 체중 대비 편차
	 * @since 1.0.0
	 */
	public int calculateWeightDiff(int height, int weight, Gender gender) {
		int standardWeight;
		if (gender.equals(Gender.M)) {
			standardWeight = (int)(Math.pow(height / 100.0, 2.0) * 22);
		} else {
			standardWeight = (int)(Math.pow(height / 100.0, 2.0) * 21);
		}
		return weight - standardWeight;
	}

}
