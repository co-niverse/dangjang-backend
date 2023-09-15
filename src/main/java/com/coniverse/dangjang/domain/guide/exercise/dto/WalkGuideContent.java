package com.coniverse.dangjang.domain.guide.exercise.dto;

import com.coniverse.dangjang.domain.guide.exercise.enums.GuideString;

import lombok.Getter;

/**
 * 걸음수 가이드
 *
 * @author EVE
 * @since 1.0.0
 */
@Getter
public class WalkGuideContent { //TODO if-else문 다른 방법으로 해결
	public String guideTTS;
	public String guideLastWeek;

	public WalkGuideContent(int needStepByTTS, int needStepByLastWeek) {
		this.guideTTS = createWalkGuideContent(needStepByTTS);
		this.guideLastWeek = createWalkGuideComparedToLastWeek(needStepByLastWeek);
	}

	/**
	 * 만보대비 걸음수에 대한 가이드 내용을 생성한다.
	 * <p>
	 * 만보 대비 얼마나 걸었는지에 대해 String으로 생성한다.
	 *
	 * @param needStepByTTS 분석한 운동 데이터
	 * @since 1.0.0
	 */

	private String createWalkGuideContent(int needStepByTTS) {

		if (needStepByTTS > 0) {
			return String.format("만보보다 %d 걸음 %s", needStepByTTS, GuideString.ENOUGH.getTenThousandStepMode());
		} else if (needStepByTTS == 0) {
			return "와우! 만보를 걸었어요";
		}
		return String.format("만보를 걷기 위해 %d 걸음 %s", needStepByTTS, GuideString.NEED_MORE.getTenThousandStepMode());

	}

	/**
	 * 저번주 대비 걸음수에 대한 가이드 내용을 생성한다.
	 * <p>
	 * 저번주 대비 얼마나 걸었는지에 대해 String으로 생성한다.
	 *
	 * @param needStepByLastWeek 지난주 대비 부족한 걸음수
	 * @since 1.0.0
	 */
	private String createWalkGuideComparedToLastWeek(int needStepByLastWeek) {
		if (needStepByLastWeek > 0) {
			return String.format("지난주 평균 걸음 수보다 %d 걸음 %s", needStepByLastWeek,
				GuideString.ENOUGH.getLastWeekMode());
		} else if (needStepByLastWeek == 0) {
			return "지난주와 동일하게 걸었어요~ 조금 더 걸어보는건 어때요?";
		}
		return String.format("지난주 평균 걸음 수보다 %d 걸음 %s", Math.abs(needStepByLastWeek),
			GuideString.ENOUGH.getLastWeekMode());

	}
}
