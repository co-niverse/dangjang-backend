package com.coniverse.dangjang.domain.guide.common.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.guide.bloodsugar.document.TodayGuide;
import com.coniverse.dangjang.domain.guide.bloodsugar.service.BloodSugarGuideSearchService;
import com.coniverse.dangjang.domain.guide.common.dto.DayGuideResponse;
import com.coniverse.dangjang.domain.guide.exercise.document.ExerciseCalorie;
import com.coniverse.dangjang.domain.guide.exercise.document.ExerciseGuide;
import com.coniverse.dangjang.domain.guide.exercise.dto.ExerciseDayGuide;
import com.coniverse.dangjang.domain.guide.exercise.service.ExerciseGuideSearchService;
import com.coniverse.dangjang.domain.guide.weight.document.WeightGuide;
import com.coniverse.dangjang.domain.guide.weight.dto.WeightDayGuide;
import com.coniverse.dangjang.domain.guide.weight.service.WeightGuideSearchService;
import com.coniverse.dangjang.domain.user.service.UserSearchService;

import lombok.RequiredArgsConstructor;

/**
 * 하루 가이드 조회 Service
 *
 * @author EVE
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class DayGuideService {
	private final UserSearchService userSearchService;
	private final BloodSugarGuideSearchService bloodSugarGuideSearchService;
	private final WeightGuideSearchService weightGuideSearchService;
	private final ExerciseGuideSearchService exerciseGuideSearchService;

	/**
	 * 하루 가이드를 조회한다.
	 * <p>
	 * 홈에 필요한 유저 닉네임,날짜,혈당 가이드,체중 가이드,운동 가이드,알림 여부를 조회해 전달한다.
	 *
	 * @param oauthId 유저아이디
	 * @param date    조회 날짜
	 * @author EVE
	 * @since 1.0.0
	 */
	public DayGuideResponse getDayGuide(String oauthId, String date) {
		LocalDate localDate = LocalDate.parse(date);
		String userNickname = userSearchService.findUserByOauthId(oauthId).getNickname();
		List<TodayGuide> bloodSugarTodayGuide = bloodSugarGuideSearchService.findByUserIdAndCreatedAt(oauthId, localDate).getTodayGuides();
		WeightGuide weightGuide = weightGuideSearchService.findByUserIdAndCreatedAt(oauthId, date);
		WeightDayGuide weightDayGuide = new WeightDayGuide(weightGuide.getUnit(), weightGuide.getBmi(), weightGuide.getWeightDiff());
		ExerciseGuide exerciseGuide = exerciseGuideSearchService.findByOauthIdAndCreatedAt(oauthId, localDate);
		int sumCalorie = exerciseGuide.getExerciseCalories().stream().mapToInt(ExerciseCalorie::calorie).sum();
		ExerciseDayGuide exerciseDayGuide = new ExerciseDayGuide(sumCalorie, exerciseGuide.getStepCount());
		return new DayGuideResponse(userNickname, localDate, bloodSugarTodayGuide, weightDayGuide, exerciseDayGuide, false);
	}
}
