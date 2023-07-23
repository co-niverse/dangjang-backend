package com.coniverse.dangjang.domain.user.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class SignUpRequest {
	private String accessToken;
	private String provider;
	private String nickname;
	private Boolean gender;
	private Date birthday;
	private int height;
	private int weight;
	private String activityAmount;
	private Boolean diabetes;
	private int diabetes_year;
	private Boolean medicine;
	private Boolean injection;
	private List<String> diseases;

}
