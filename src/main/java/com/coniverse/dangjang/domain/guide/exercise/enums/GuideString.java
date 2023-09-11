package com.coniverse.dangjang.domain.guide.exercise.enums;

/**
 * 운동 가이드 문자열
 *
 * @author EVE
 * @since 1.0.0
 */
public enum GuideString {

	NEEDMORE("부족해요! 조금만 더 걸어볼까요?", "덜 걸었어요~ "),
	ENOUGH("더 걸었군요! 멋져요~", "더 걸었어요. 좋아요~");
	// WLK_NEEDMORE("부족해요! 조금만 더 걸어볼까요?"),
	// WLK_enough("더 걸었군요! 멋져요~"),
	// LSTWK_NEEDMORE("덜 걸었어요~ "),
	// LSTWK_enough("더 걸었어요. 좋아요~"),

	private final String TTSMode;
	private final String lastWeekMode;

	GuideString(String TTSMode, String lastWeekMode) {
		this.TTSMode = TTSMode;
		this.lastWeekMode = lastWeekMode;
	}

	public String getTTSMode() {
		return TTSMode;
	}

	public String getLastWeekMode() {
		return lastWeekMode;
	}
}
