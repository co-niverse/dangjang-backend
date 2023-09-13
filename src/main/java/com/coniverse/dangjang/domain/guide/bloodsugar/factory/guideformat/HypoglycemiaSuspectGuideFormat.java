package com.coniverse.dangjang.domain.guide.bloodsugar.factory.guideformat;

import lombok.AllArgsConstructor;

/**
 * 저혈당 의심 가이드 format
 *
 * @author TEO
 * @since 1.0.0
 */

@AllArgsConstructor
public class HypoglycemiaSuspectGuideFormat implements GuideFormat {
	private int deviation;

	@Override
	public String getTitle() {
		return String.format("혈당이 정상수치보다 낮아요. %s만큼 높여야해요", deviation);
	}

	@Override
	public String getContent() {
		return "휴식을 취하고 당이 포함된 음식을 섭취해주세요. 🍬";
	}
}
