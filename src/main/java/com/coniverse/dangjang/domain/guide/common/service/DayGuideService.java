package com.coniverse.dangjang.domain.guide.common.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.guide.bloodsugar.document.TodayGuide;
import com.coniverse.dangjang.domain.guide.bloodsugar.service.BloodSugarGuideSearchService;
import com.coniverse.dangjang.domain.guide.common.dto.response.DayGuideResponse;
import com.coniverse.dangjang.domain.guide.common.dto.response.UserLog;
import com.coniverse.dangjang.domain.guide.common.exception.GuideNotFoundException;
import com.coniverse.dangjang.domain.guide.exercise.document.ExerciseCalorie;
import com.coniverse.dangjang.domain.guide.exercise.document.ExerciseGuide;
import com.coniverse.dangjang.domain.guide.exercise.dto.ExerciseDayGuide;
import com.coniverse.dangjang.domain.guide.exercise.service.ExerciseGuideSearchService;
import com.coniverse.dangjang.domain.guide.weight.document.WeightGuide;
import com.coniverse.dangjang.domain.guide.weight.dto.WeightDayGuide;
import com.coniverse.dangjang.domain.guide.weight.service.WeightGuideSearchService;
import com.coniverse.dangjang.domain.notification.service.NotificationService;
import com.coniverse.dangjang.domain.user.entity.User;
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
	private final NotificationService notificationService;

	/**
	 * 하루 가이드를 조회한다.
	 * <p>
	 * 홈에 필요한 유저 닉네임,날짜,혈당 가이드,체중 가이드,운동 가이드,알림 여부를 조회해 전달한다.
	 *
	 * @param oauthId 유저아이디
	 * @param date    조회 날짜
	 * @return DayGuideResponse
	 * @since 1.0.0
	 */
	public DayGuideResponse getDayGuide(String oauthId, String date) {
		LocalDate localDate = LocalDate.parse(date);
		User user = userSearchService.findUserByOauthId(oauthId);
		String userNickname = user.getNickname();
		List<TodayGuide> bloodSugarTodayGuide = getBloodSugarTodayGuide(oauthId, localDate);
		WeightDayGuide weightDayGuide = getWeightGuide(oauthId, date);
		ExerciseDayGuide exerciseDayGuide = getExerciseGuide(oauthId, localDate);
		Boolean existsNotification = notificationService.isExistsNotReadNotification(oauthId);
		UserLog userLog = new UserLog(user.getGender().isTrue(), user.getBirthYear(), user.isDiabetic(), user.getDiabetesYear(), weightDayGuide.alert());
		return new DayGuideResponse(userNickname, localDate, bloodSugarTodayGuide, weightDayGuide, exerciseDayGuide, existsNotification, userLog);
	}

	/**
	 * 혈당 하루 가이드를 조회한다.
	 *
	 * @param oauthId 유저아이디
	 * @param date    조회 날짜
	 * @return List<TodayGuide> 혈당 하루 가이드
	 * @since 1.0.0
	 */
	private List<TodayGuide> getBloodSugarTodayGuide(String oauthId, LocalDate date) {
		try {
			return bloodSugarGuideSearchService.findByUserIdAndCreatedAt(oauthId, date).getTodayGuides();
		} catch (GuideNotFoundException e) {
			return new ArrayList<>();
		}
	}

	/**
	 * 체중 하루 가이드를 조회한다.
	 *
	 * @param oauthId 유저아이디
	 * @param date    조회 날짜
	 * @return WeightDayGuide 체중 하루 가이드
	 * @since 1.0.0
	 */
	private WeightDayGuide getWeightGuide(String oauthId, String date) {
		try {
			WeightGuide weightGuide = weightGuideSearchService.findByUserIdAndCreatedAt(oauthId, date);
			return new WeightDayGuide(weightGuide.getUnit(), weightGuide.getBmi(), weightGuide.getContent(), weightGuide.getAlert().getTitle());
		} catch (GuideNotFoundException e) {
			return new WeightDayGuide("", 0, "", "");
		}
	}

	/**
	 * 운동 하루 가이드를 조회한다.
	 *
	 * @param oauthId 유저아이디
	 * @param date    조회 날짜
	 * @return ExerciseDayGuide 운동 하루 가이드
	 * @since 1.0.0
	 */
	private ExerciseDayGuide getExerciseGuide(String oauthId, LocalDate date) {
		try {
			ExerciseGuide exerciseGuide = exerciseGuideSearchService.findByOauthIdAndCreatedAt(oauthId, date);
			int sumCalorie = exerciseGuide.getExerciseCalories()
				.stream()
				.mapToInt(ExerciseCalorie::calorie)
				.sum();
			return new ExerciseDayGuide(sumCalorie, exerciseGuide.getStepCount());
		} catch (GuideNotFoundException e) {
			return new ExerciseDayGuide(0, 0);
		}
	}
}
