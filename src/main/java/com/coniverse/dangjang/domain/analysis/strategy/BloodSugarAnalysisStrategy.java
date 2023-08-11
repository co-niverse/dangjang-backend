package com.coniverse.dangjang.domain.analysis.strategy;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.coniverse.dangjang.domain.analysis.dto.healthMetric.BloodSugarAnalysisData;
import com.coniverse.dangjang.domain.analysis.enums.Alert;
import com.coniverse.dangjang.domain.analysis.enums.BloodSugarLevel;
import com.coniverse.dangjang.domain.analysis.exception.NonAnalyticDataException;
import com.coniverse.dangjang.domain.code.enums.GroupCode;
import com.coniverse.dangjang.domain.feedback.dto.response.FeedbackResponse;
import com.coniverse.dangjang.domain.feedback.service.BloodSugarFeedbackService;

import lombok.RequiredArgsConstructor;

/**
 * 혈당 분석 전략
 *
 * @author TEO
 * @see BloodSugarAnalysisData
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class BloodSugarAnalysisStrategy implements AnalysisStrategy<BloodSugarAnalysisData, FeedbackResponse> {
	private final BloodSugarFeedbackService bloodSugarFeedbackService;

	/**
	 * 혈당 데이터를 분석한다.
	 *
	 * @param data 혈당 분석 데이터
	 * @throws NonAnalyticDataException 분석할 수 없는 데이터일 때 발생하는 예외
	 * @since 1.0.0
	 */
	@Override
	public FeedbackResponse analyze(BloodSugarAnalysisData data) { // TODO 운동부족
		int unit = data.getUnit();

		return bloodSugarFeedbackService.save(
			Arrays.stream(BloodSugarLevel.values())
				.filter(levels -> levels.isDiabetic() == data.isDiabetic())
				.filter(levels -> levels.contains(data.getType()))
				.findAny()
				.map(level -> {
					this.calculateAlertAndDeviation(level, data);
					return data;
				})
				.orElseThrow(NonAnalyticDataException::new)
		);
	}

	/**
	 * 혈당 그룹 코드를 반환한다.
	 *
	 * @see GroupCode
	 * @since 1.0.0
	 */
	@Override
	public GroupCode getCodeGroup() {
		return GroupCode.BLOOD_SUGAR;
	}

	/**
	 * unit을 기준으로 경보와 편차를 계산한다.
	 *
	 * @param level 혈당 판별 기준
	 * @param data  혈당 분석 데이터
	 * @see BloodSugarLevel
	 */
	private void calculateAlertAndDeviation(BloodSugarLevel level, BloodSugarAnalysisData data) {
		int unit = data.getUnit();
		int normalMinCriteria = level.getNormalMinCriteria();
		int normalMaxCriteria = level.getNormalMaxCriteria();
		int cautionCriteria = level.getCautionCriteria();

		Alert alert;
		int deviation;
		if (unit < 50) {
			alert = Alert.HYPOGLYCEMIA;
			deviation = normalMinCriteria - unit;
		} else if (unit < normalMinCriteria) {
			alert = Alert.CAUTION;
			deviation = normalMinCriteria - unit;
		} else if (unit <= normalMaxCriteria) {
			alert = Alert.NORMAL;
			deviation = 0;
		} else if (unit < cautionCriteria) {
			alert = Alert.CAUTION;
			deviation = normalMaxCriteria - unit;
		} else {
			alert = Alert.WARNING;
			deviation = normalMaxCriteria - unit;
		}
		data.setAlert(alert);
		data.setDeviation(deviation);
	}
}
