package com.coniverse.dangjang.domain.guide.common.dto.response;

import java.time.LocalDate;
import java.util.List;

import com.coniverse.dangjang.domain.guide.bloodsugar.document.TodayGuide;
import com.coniverse.dangjang.domain.guide.exercise.dto.ExerciseDayGuide;
import com.coniverse.dangjang.domain.guide.weight.dto.WeightDayGuide;
import com.coniverse.dangjang.domain.user.dto.response.UserDiabeticResponse;

/**
 * 하루 가이드 조회 응답
 *
 * @author EVE
 * @since 1.0.0
 */
public record DayGuideResponse(String nickname, LocalDate date, List<TodayGuide> bloodSugars, WeightDayGuide weight, ExerciseDayGuide exercise,
							   boolean notification, UserDiabeticResponse userLog) {

}
