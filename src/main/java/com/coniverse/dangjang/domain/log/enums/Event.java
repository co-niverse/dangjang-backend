package com.coniverse.dangjang.domain.log.enums;

import com.coniverse.dangjang.global.support.enums.EnumCode;

import lombok.AllArgsConstructor;

/**
 * event enum
 *
 * @author TEO
 * @since 1.0.0
 */
@AllArgsConstructor
public enum Event implements EnumCode {
	HOME_BLOOD_SUGAR_CLICK("home_bloodsugar_click"),
	HOME_WEIGHT_CLICK("home_weight_click"),
	HOME_EXERCISE_CLICK("home_exercise_click"),
	HOME_CALORIE_CLICK("home_calorie_click"),
	HOME_CALENDAR_CLICK("home_calendar_click"),
	BLOOD_SUGAR_STAY_TIME("bloodsugar_stay_time"),
	HEALTH_CONNECT_JOIN_CLICK("healthconnect_join_click"),
	HEALTH_CONNECT_MANUAL("healthconnect_manual"),
	SIGNUP_STAY_TIME("signup_stay_time"),
	SIGNUP_AFTER_TIME("signup_after_time"),
	EXPOSURE_LOG("exposure_log");

	private final String title;

	@Override
	public String getCode() {
		return name();
	}

	@Override
	public String getTitle() {
		return this.title;
	}
}
