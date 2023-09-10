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

@Component
public class BloodSugarGuideFormatFactory {
	public GuideFormat createGuideFormat(BloodSugarAnalysisData data) {
		Alert alert = data.getAlert();
		return switch (alert) {
			case HYPOGLYCEMIA -> createHypoglycemiaGuideFormat(data);
			case HYPOGLYCEMIA_SUSPECT -> createHypoglycemiaSuspectGuideFormat(data);
			case NORMAL -> createNormalGuideFormat();
			case CAUTION -> createCautionGuideFormat(data);
			case WARNING -> createWarningGuideFormat(data);
			default -> throw new IllegalStateException("Unexpected value: " + alert);
		};
	}

	private GuideFormat createHypoglycemiaGuideFormat(BloodSugarAnalysisData data) {
		return Arrays.stream(HypoglycemiaGuideFormat.values())
			.filter(format -> format.verifyMedicine(data.isMedicine()))
			.filter(format -> format.verifyInjection(data.isInjection()))
			.findAny()
			.orElseThrow();
	}

	private GuideFormat createHypoglycemiaSuspectGuideFormat(BloodSugarAnalysisData data) {
		return new HypoglycemiaSuspectGuideFormat(data.getDeviation());
	}

	public GuideFormat createNormalGuideFormat() {
		return NormalGuideFormat.getRandomOne();
	}

	private GuideFormat createCautionGuideFormat(BloodSugarAnalysisData data) {
		CautionGuideFormat format = new CautionGuideFormat(data.getDeviation(), data.isLackOfExercise(), data.isOverweight());
		format.setContent(data.isDiabetic(), data.getType());
		return format;
	}

	private GuideFormat createWarningGuideFormat(BloodSugarAnalysisData data) {
		WarningGuideFormat format = new WarningGuideFormat(data.getDeviation(), data.isLackOfExercise(), data.isOverweight());
		format.setContent(data.isDiabetic());
		return format;
	}
}
