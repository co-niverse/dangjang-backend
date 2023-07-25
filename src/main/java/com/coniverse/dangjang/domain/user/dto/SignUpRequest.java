package com.coniverse.dangjang.domain.user.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

/**
 * 회원가입 Request Parm
 *
 * @author EVE
 * @since 1.0
 */
@Data
public class SignUpRequest {
	private String accessToken;
	private String provider;
	private String nickname;
	private Boolean gender;
	private LocalDate birthday;
	private int height;
	private int weight;
	private String activityAmount;
	private Boolean diabetes;
	private int diabetes_year;
	private Boolean medicine;
	private Boolean injection;
	private List<String> diseases;

}
