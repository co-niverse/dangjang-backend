package com.coniverse.dangjang.domain.guide.bloodsugar.factory.guideformat;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import lombok.AllArgsConstructor;

/**
 * 정상 가이드 format
 *
 * @author TEO
 * @since 1.0.0
 */
@AllArgsConstructor
public enum NormalGuideFormat implements GuideFormat {
	GUIDE_1("지금처럼 열심히 관리해보아요!"),
	GUIDE_2("잘 조절하고 있어요. 조금만 더 화이팅!"),
	GUIDE_3("열심히 관리하는 모습, 아주 멋져요👍🏼");

	private final String title;

	/**
	 * 가이드 중 하나를 랜덤으로 반환한다.
	 *
	 * @return 정상 가이드 format
	 * @since 1.0.0
	 */
	public static NormalGuideFormat getRandomOne() {
		int idx = ThreadLocalRandom.current().nextInt(3);
		return Arrays.stream(NormalGuideFormat.values()).toList().get(idx);
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public String getContent() {
		return "";
	}
}
