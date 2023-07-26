package com.coniverse.dangjang.domain.user.dto;

import java.time.LocalDate;
import java.util.List;

/**
 * 회원가입 Request Parm
 *
 * @author EVE
 * @since 1.0
 */

public record SignUpRequest(String accessToken, String provider, String nickname, Boolean gender, LocalDate birthday, int height, int weight,
							String activityAmount, Boolean diabetes, int diabetes_year, Boolean medicine, Boolean injection, List<String> diseases) {

}
