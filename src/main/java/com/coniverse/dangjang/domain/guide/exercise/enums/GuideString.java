package com.coniverse.dangjang.domain.guide.exercise.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 운동 가이드 문자열
 *
 * @author EVE
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum GuideString {

	NEEDMORE("부족해요! 조금만 더 걸어볼까요?", "덜 걸었어요~ "),
	ENOUGH("더 걸었군요! 멋져요~", "더 걸었어요. 좋아요~");

	private final String TTSMode;
	private final String lastWeekMode;

	// public String getTTSMode() {
	// 	return TTSMode;
	// }
	//
	// public String getLastWeekMode() {
	// 	return lastWeekMode;
	// }
}
