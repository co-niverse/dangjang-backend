package com.coniverse.dangjang.fixture;

import static com.coniverse.dangjang.fixture.CommonCodeFixture.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.coniverse.dangjang.domain.analysis.enums.Alert;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.guide.bloodsugar.document.BloodSugarGuide;
import com.coniverse.dangjang.domain.guide.bloodsugar.document.SubGuide;
import com.coniverse.dangjang.domain.guide.bloodsugar.document.TodayGuide;
import com.coniverse.dangjang.domain.guide.bloodsugar.dto.BloodSugarGuideResponse;
import com.coniverse.dangjang.domain.guide.bloodsugar.dto.SubGuideResponse;
import com.coniverse.dangjang.domain.guide.common.dto.GuideResponse;
import com.coniverse.dangjang.domain.guide.exercise.document.ExerciseGuide;
import com.coniverse.dangjang.domain.guide.exercise.dto.ExerciseCalorie;
import com.coniverse.dangjang.domain.guide.exercise.dto.ExerciseGuideResponse;
import com.coniverse.dangjang.domain.guide.weight.document.WeightGuide;
import com.coniverse.dangjang.domain.guide.weight.dto.WeightGuideResponse;

/**
 * 가이드 fixture
 *
 * @author TEO, EVE
 * @since 1.0.0
 */
public class GuideFixture {
	public static BloodSugarGuide 혈당_가이드_도큐먼트(String oauthId, String createdAt) {
		BloodSugarGuide guide = BloodSugarGuide.builder()
			.oauthId(oauthId)
			.createdAt(createdAt)
			.build();

		혈당_타입().stream()
			.map(GuideFixture::혈당_서브_가이드)
			.forEach(guide::addSubGuide);
		return guide;
	}

	public static SubGuide 혈당_서브_가이드(CommonCode type) {
		return SubGuide.builder()
			.type(type)
			.alert(Alert.CAUTION.getTitle())
			.unit("100")
			.title("가이드 제목입니다.")
			.content("가이드 내용입니다.")
			.build();
	}

	public static List<TodayGuide> 혈당_오늘의_가이드(String createdAt) {
		return 혈당_가이드_도큐먼트("11111111", createdAt).getTodayGuides();
	}

	public static WeightGuide 체중_가이드(String oauthId, String createdAt) {
		return WeightGuide.builder()
			.oauthId(oauthId)
			.createdAt(createdAt)
			.bmi(18.5)
			.unit("80")
			.weightDiff(10)
			.alert(Alert.LEVEL_1_OBESITY)
			.content("가이드 내용입니다.")
			.build();
	}

	public static ExerciseGuide 운동_가이드(String oauthId, String 조회_날짜) {
		List<ExerciseCalorie> exerciseCalories = List.of(new ExerciseCalorie(CommonCode.HEALTH, 100, 60), new ExerciseCalorie(CommonCode.RUN, 200, 120));

		return ExerciseGuide.builder()
			.oauthId(oauthId)
			.needStepByLastWeek(0)
			.comparedToLastWeek("저번주 대비 가이드입니다.")
			.content("가이드 내용입니다.")
			.createdAt(조회_날짜)
			.stepsCount(0)
			.exerciseCalories(exerciseCalories)
			.build();
	}

	public static BloodSugarGuideResponse 혈당_가이드_응답(String createdAt) {
		List<TodayGuide> 오늘의_가이드 = 혈당_오늘의_가이드(createdAt);
		List<SubGuideResponse> 서브_가이드 = new ArrayList<>();
		서브_가이드.add((SubGuideResponse)혈당_가이드_응답용_서브_가이드_응답());
		return new BloodSugarGuideResponse(createdAt, 오늘의_가이드, 서브_가이드);
	}

	public static GuideResponse 혈당_가이드_응답용_서브_가이드_응답() {
		return new SubGuideResponse(CommonCode.BEFORE_BREAKFAST.getTitle(), "100", Alert.CAUTION.getTitle(), "제목입니다", "가이드입니다", null);
	}

	public static GuideResponse 혈당_서브_가이드_응답() {
		return new SubGuideResponse(CommonCode.BEFORE_BREAKFAST.getTitle(), null, Alert.CAUTION.getTitle(), "제목입니다", "가이드입니다",
			혈당_오늘의_가이드(LocalDate.now().toString()));
	}

	public static WeightGuideResponse 체중_가이드_응답(String createdAt) {
		return new WeightGuideResponse(CommonCode.MEASUREMENT.getTitle(), createdAt, 20, Alert.LEVEL_1_OBESITY.getTitle(), "가이드입니다", 18.0,
			"50");
	}

	public static ExerciseGuideResponse 운동_가이드_응답(String 조회_날짜) {
		List<ExerciseCalorie> exerciseCalories = List.of(new ExerciseCalorie(CommonCode.HEALTH, 100, 60), new ExerciseCalorie(CommonCode.RUN, 200, 120));
		return new ExerciseGuideResponse(조회_날짜, 5, 55, "저번주 대비 가이드입니다.", "가이드 내용입니다.", 0, exerciseCalories);
	}

	public static GuideResponse 당화혈색소_가이드_응답() { // TODO return 수정
		return null;
	}

}
