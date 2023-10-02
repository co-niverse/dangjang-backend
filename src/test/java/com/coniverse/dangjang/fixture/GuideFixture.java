package com.coniverse.dangjang.fixture;

import static com.coniverse.dangjang.fixture.CommonCodeFixture.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.coniverse.dangjang.domain.analysis.enums.Alert;
import com.coniverse.dangjang.domain.analysis.enums.ExerciseCoefficient;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.guide.bloodsugar.document.BloodSugarGuide;
import com.coniverse.dangjang.domain.guide.bloodsugar.document.SubGuide;
import com.coniverse.dangjang.domain.guide.bloodsugar.document.TodayGuide;
import com.coniverse.dangjang.domain.guide.bloodsugar.dto.BloodSugarGuideResponse;
import com.coniverse.dangjang.domain.guide.bloodsugar.dto.SubGuideResponse;
import com.coniverse.dangjang.domain.guide.common.dto.DayGuideResponse;
import com.coniverse.dangjang.domain.guide.common.dto.GuideResponse;
import com.coniverse.dangjang.domain.guide.exercise.document.ExerciseCalorie;
import com.coniverse.dangjang.domain.guide.exercise.document.ExerciseGuide;
import com.coniverse.dangjang.domain.guide.exercise.dto.ExerciseDayGuide;
import com.coniverse.dangjang.domain.guide.exercise.dto.ExerciseGuideResponse;
import com.coniverse.dangjang.domain.guide.weight.document.WeightGuide;
import com.coniverse.dangjang.domain.guide.weight.dto.WeightDayGuide;
import com.coniverse.dangjang.domain.guide.weight.dto.WeightGuideResponse;
import com.coniverse.dangjang.domain.user.entity.User;

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

	public static ExerciseGuide 운동_가이드(String oauthId, LocalDate 조회_날짜) {
		List<ExerciseCalorie> exerciseCalories = List.of(new ExerciseCalorie(CommonCode.HEALTH, 100, 60), new ExerciseCalorie(CommonCode.RUN, 200, 120));

		return ExerciseGuide.builder()
			.oauthId(oauthId)
			.needStepByLastWeek(0)
			.comparedToLastWeek("저번주 대비 가이드입니다.")
			.content("가이드 내용입니다.")
			.createdAt(조회_날짜)
			.stepCount(0)
			.exerciseCalories(exerciseCalories)
			.build();
	}

	public static ExerciseGuide 걸음수_운동_가이드(String oauthId, LocalDate 조회_날짜) {
		List<ExerciseCalorie> exerciseCalories = List.of(new ExerciseCalorie(CommonCode.HEALTH, 0, 60), new ExerciseCalorie(CommonCode.RUN, 0, 120));

		return ExerciseGuide.builder()
			.oauthId(oauthId)
			.needStepByLastWeek(0)
			.comparedToLastWeek("저번주 대비 가이드입니다.")
			.content("가이드 내용입니다.")
			.createdAt(조회_날짜)
			.stepCount(10000)
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

	public static ExerciseCalorie 운동_칼로리(CommonCode type, int unit, int weight) {
		double percent = ExerciseCoefficient.findCoefficientByType(type);
		int calorie = (int)(percent * weight / 15 * unit);
		return new ExerciseCalorie(type, calorie, unit);
	}

	public static GuideResponse 당화혈색소_가이드_응답() { // TODO return 수정
		return null;
	}

	public static List<ExerciseGuide> 운동가이드_리스트(User user, LocalDate createdAt, int unit, int needCount) {
		return Stream.iterate(0, i -> i + 1).limit(needCount)
			.map(n -> ExerciseGuide.builder()
				.oauthId(user.getOauthId())
				.comparedToLastWeek("저번주 대비 가이드입니다.")
				.content("가이드 내용입니다.").exerciseCalories(List.of(
					운동_칼로리(CommonCode.HEALTH, unit + (n * 10), 70),
					운동_칼로리(CommonCode.RUN, unit + (n * 10), 70)
				)).needStepByLastWeek(2000)
				.needStepByTTS(2000)
				.stepCount(8000)
				.createdAt(createdAt.plusDays(n))
				.build()).collect(Collectors.toList());

	}

	public static DayGuideResponse 하루_가이드_응답(String nickname, LocalDate date, List<TodayGuide> 혈당가이드, WeightDayGuide 체중가이드, ExerciseDayGuide 운동가이드) {
		return new DayGuideResponse(nickname, date, 혈당가이드, 체중가이드, 운동가이드, false);
	}

	public static WeightDayGuide 체중_하루_가이드() {
		return new WeightDayGuide("70", 18.89, "정상이에요! 표준 체중보다 1kg 많아요");
	}

	public static ExerciseDayGuide 운동_하루_가이드() {
		return new ExerciseDayGuide(1400, 11000);
	}

}
