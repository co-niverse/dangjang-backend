package com.coniverse.dangjang.domain.guide.bloodsugar.factory.guideformat;

import java.util.Arrays;

import lombok.AllArgsConstructor;

/**
 * 경고 가이드 format
 *
 * @author TEO
 * @since 1.0.0
 */
public class WarningGuideFormat implements GuideFormat {
	private String content;
	private final int deviation;
	private final boolean lackOfExercise;
	private final boolean overweight;

	public WarningGuideFormat(int deviation, boolean lackOfExercise, boolean overweight) {
		this.deviation = deviation;
		this.lackOfExercise = lackOfExercise;
		this.overweight = overweight;
	}

	public void setContent(boolean diabetic) {
		Arrays.stream(Content.values())
			.filter(c -> c.diabetic == diabetic)
			.findFirst()
			.ifPresent(c -> this.content = c.body);
	}

	@Override
	public String getTitle() {
		return String.format("혈당 수치가 심각하게 높아요! %d만큼 낮춰야해요", deviation);
	}

	@Override
	public String getContent() {
		if (this.lackOfExercise) {
			this.content = String.format("%s%n운동량이 부족하면 혈당 수치가 높아질 수 있어요. 🏃🏻", this.content);
		}
		if (this.overweight) {
			this.content = String.format("%s%n과체중은 혈당 수치에 영향을 줄 수 있어요. 체중 조절이 필요해요!", this.content);
		}
		return this.content;
	}

	@AllArgsConstructor
	private enum Content {
		PRE_DIABETES(false, "당뇨병이 의심돼요. 병원 내방을 권고드려요!"),
		DIABETES(true, "혈당 수치 관리를 더더욱 신경써야 해요!");

		private final boolean diabetic;
		private final String body;
	}
}
