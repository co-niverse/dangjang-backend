package com.coniverse.dangjang.domain.guide.bloodsugar.factory.guideformat;

import java.util.Arrays;

import com.coniverse.dangjang.domain.analysis.enums.Meal;
import com.coniverse.dangjang.domain.code.enums.CommonCode;

import lombok.AllArgsConstructor;

/**
 * 주의 가이드 format
 *
 * @author TEO
 * @since 1.0.0
 */
public class CautionGuideFormat implements GuideFormat {
	private final int deviation;
	private final boolean lackOfExercise;
	private final boolean overweight;
	private String content;

	public CautionGuideFormat(int deviation, boolean lackOfExercise, boolean overweight) {
		this.deviation = deviation;
		this.lackOfExercise = lackOfExercise;
		this.overweight = overweight;
	}

	public void setContent(boolean diabetic, CommonCode type) {
		Arrays.stream(Content.values())
			.filter(c -> c.diabetic == diabetic && c.meal.contains(type))
			.findFirst()
			.ifPresent(c -> this.content = c.content);
	}

	@Override
	public String getTitle() {
		return String.format("혈당이 정상수치보다 높아요. %d만큼 낮춰야해요", deviation);
	}

	@Override
	public String getContent() {
		String content = this.content;
		if (this.lackOfExercise) {
			content = String.format("%s%n운동량이 부족하면 혈당 수치가 높아질 수 있어요. 🏃🏻", content);
		}
		if (this.overweight) {
			content = String.format("%s%n과체중은 혈당 수치에 영향을 줄 수 있어요. 체중 조절이 필요해요!", content);
		}
		return content;
	}

	/**
	 * 당뇨 여부, 식전/식후 혈당에 따른 가이드 내용
	 *
	 * @since 1.0.0
	 */
	@AllArgsConstructor
	private enum Content {
		PRE_DIABETES_BM(false, Meal.BEFORE_MEAL, "공복 혈당 장애가 의심돼요."),
		PRE_DIABETES_AM(false, Meal.AFTER_MEAL, "내당능 장애가 의심돼요."),
		DIABETES_BM(true, Meal.BEFORE_MEAL, "정상 수치까지 조금만 노력해볼까요?"),
		DIABETES_AM(true, Meal.AFTER_MEAL, "정상 수치까지 조금만 노력해볼까요?");

		private final boolean diabetic;
		private final Meal meal;
		private final String content;
	}
}
