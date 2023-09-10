package com.coniverse.dangjang.domain.guide.bloodsugar.factory;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.coniverse.dangjang.domain.analysis.dto.healthMetric.BloodSugarAnalysisData;
import com.coniverse.dangjang.domain.analysis.enums.Alert;
import com.coniverse.dangjang.domain.guide.bloodsugar.factory.guideformat.CautionGuideFormat;
import com.coniverse.dangjang.domain.guide.bloodsugar.factory.guideformat.GuideFormat;
import com.coniverse.dangjang.domain.guide.bloodsugar.factory.guideformat.HypoglycemiaGuideFormat;
import com.coniverse.dangjang.domain.guide.bloodsugar.factory.guideformat.HypoglycemiaSuspectGuideFormat;
import com.coniverse.dangjang.domain.guide.bloodsugar.factory.guideformat.NormalGuideFormat;
import com.coniverse.dangjang.domain.guide.bloodsugar.factory.guideformat.WarningGuideFormat;
import com.coniverse.dangjang.global.exception.ServerErrorException;

/**
 * 혈당 가이드 format factory
 *
 * @author TEO
 * @since 1.0.0
 */
@Component
public class BloodSugarGuideFormatFactory {
	/**
	 * data의 경보에 따라 가이드 format을 생성한다.
	 *
	 * @param data 혈당 분석 데이터
	 * @return 가이드 format
	 * @since 1.0.0
	 */
	public GuideFormat createGuideFormat(BloodSugarAnalysisData data) { // TODO factory method pattern
		Alert alert = data.getAlert();
		return switch (alert) {
			case HYPOGLYCEMIA -> createHypoglycemiaGuideFormat(data);
			case HYPOGLYCEMIA_SUSPECT -> createHypoglycemiaSuspectGuideFormat(data);
			case NORMAL -> createNormalGuideFormat();
			case CAUTION -> createCautionGuideFormat(data);
			case WARNING -> createWarningGuideFormat(data);
			default -> throw new ServerErrorException("Unexpected value: " + alert);
		};
	}

	/**
	 * 저혈당 가이드 format을 생성한다.
	 *
	 * @param data 혈당 분석 데이터
	 * @return 저혈당 가이드 format
	 * @since 1.0.0
	 */
	private GuideFormat createHypoglycemiaGuideFormat(BloodSugarAnalysisData data) {
		return Arrays.stream(HypoglycemiaGuideFormat.values())
			.filter(format -> format.verifyMedicine(data.isMedicine()))
			.filter(format -> format.verifyInjection(data.isInjection()))
			.findAny()
			.orElseThrow();
	}

	/**
	 * 저혈당 의심 가이드 format을 생성한다.
	 *
	 * @param data 혈당 분석 데이터
	 * @return 저혈당 의심 가이드 format
	 * @since 1.0.0
	 */
	private GuideFormat createHypoglycemiaSuspectGuideFormat(BloodSugarAnalysisData data) {
		return new HypoglycemiaSuspectGuideFormat(data.getDeviation());
	}

	/**
	 * 정상 가이드 format을 생성한다.
	 *
	 * @return 정상 가이드 format
	 * @since 1.0.0
	 */
	public GuideFormat createNormalGuideFormat() {
		return NormalGuideFormat.getRandomOne();
	}

	/**
	 * 주의 가이드 format을 생성한다.
	 *
	 * @param data 혈당 분석 데이터
	 * @return 주의 가이드 format
	 * @since 1.0.0
	 */
	private GuideFormat createCautionGuideFormat(BloodSugarAnalysisData data) {
		CautionGuideFormat format = new CautionGuideFormat(data.getDeviation(), data.isLackOfExercise(), data.isOverweight());
		format.setContent(data.isDiabetic(), data.getType());
		return format;
	}

	/**
	 * 경고 가이드 format을 생성한다.
	 *
	 * @param data 혈당 분석 데이터
	 * @return 경고 가이드 format
	 * @since 1.0.0
	 */
	private GuideFormat createWarningGuideFormat(BloodSugarAnalysisData data) {
		WarningGuideFormat format = new WarningGuideFormat(data.getDeviation(), data.isLackOfExercise(), data.isOverweight());
		format.setContent(data.isDiabetic());
		return format;
	}
}
