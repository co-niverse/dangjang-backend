package com.coniverse.dangjang.domain.guide.bloodsugar.factory.guideformat;

import lombok.AllArgsConstructor;

/**
 * 저혈당 가이드 format
 *
 * @author TEO
 * @since 1.0.0
 */
@AllArgsConstructor
public enum HypoglycemiaGuideFormat implements GuideFormat {
	NO_MEDICINE_AND_INJECTION(false, false, "경구용 혈당강하제나 인슐린 치료를 받지 않고 있는 사람은 하루 빨리 병원에 방문해야 해요!"),
	ONLY_MEDICINE(true, false, "경구용 혈당강하제를 사용하고 있어요.%n복용량을 조절해야 해요!"),
	ONLY_INJECTION(false, true, "인슐린을 사용하고 있어요.%n주입량을 조절해야 해요!"),
	MEDICINE_AND_INJECTION(true, true, "경구용 혈당강하제와 인슐린을 사용하고 있어요.%n복용량과 주입량을 조절해야 해요!");

	private final boolean medicine;
	private final boolean injection;
	private final String content;

	@Override
	public String getTitle() {
		return "당이 포함된 음식을 빨리 섭취해주세요!";
	}

	@Override
	public String getContent() {
		return this.content;
	}

	public boolean verifyMedicine(boolean medicine) {
		return this.medicine == medicine;
	}

	public boolean verifyInjection(boolean injection) {
		return this.injection == injection;
	}
}
