package com.coniverse.dangjang.domain.user.dto;

import java.util.Date;

import lombok.Data;

@Data
public class SignUpRequest {
	private String accessToken;
	private String nickname;
	private Date birthday;
	private String activityAmount;
	private int height;
	private int recommended_calorie;
	private Boolean diabetes;
	private int diabetes_year;
	private Boolean medicine;
	private Boolean injection;
}
