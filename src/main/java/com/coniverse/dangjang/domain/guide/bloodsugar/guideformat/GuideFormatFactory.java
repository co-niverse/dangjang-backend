package com.coniverse.dangjang.domain.guide.bloodsugar.guideformat;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.coniverse.dangjang.domain.analysis.dto.healthMetric.BloodSugarAnalysisData;
import com.coniverse.dangjang.domain.analysis.enums.Alert;
import com.coniverse.dangjang.domain.guide.bloodsugar.guideformat.format.CautionGuideFormat;
import com.coniverse.dangjang.domain.guide.bloodsugar.guideformat.format.GuideFormat;
import com.coniverse.dangjang.domain.guide.bloodsugar.guideformat.format.HypoglycemiaGuideFormat;
import com.coniverse.dangjang.domain.guide.bloodsugar.guideformat.format.HypoglycemiaSuspectGuideFormat;
import com.coniverse.dangjang.domain.guide.bloodsugar.guideformat.format.NormalGuideFormat;
import com.coniverse.dangjang.domain.guide.bloodsugar.guideformat.format.WarningGuideFormat;

@Component
public class GuideFormatFactory {
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
