package com.coniverse.dangjang.domain.guide.exercise.enums;

/**
 * 운동 가이드 문자열
 *
 * @author EVE
 * @since 1.0.0
 */
public enum GuideString {
	WLK_needMore("부족해요! 조금만 더 걸어볼까요?"),
	WLK_enough("더 걸었군요! 멋져요~"),
	LSTWK_needMore("덜 걸었어요~ "),
	LSTWK_enough("더 걸었어요. 좋아요~"),
	;

	private final String guide;

	GuideString(String guide) {
		this.guide = guide;
	}

	public String getGuide() {
		return guide;
	}
}
