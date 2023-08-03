package com.coniverse.dangjang.fixture;

import java.time.LocalDate;
import java.util.List;

import com.coniverse.dangjang.domain.user.dto.request.SignUpRequest;

/**
 * 테스트에서 필요한 Request를 반환해주는 메소드를 가진 클래스
 *
 * @author EVE
 * @since 1.0.0
 */
public class SignUpFixture {

	public static SignUpRequest getSignUpRequest(String accessToken, String nickname, String provider, Boolean gender, LocalDate birthday, int height,
		int weight, String activityAmount, Boolean diabetes, int diabetesYear, Boolean medicine, Boolean injection,
		List<String> diseases) {
		SignUpRequest signUpRequest = new SignUpRequest(accessToken, provider, nickname, gender, birthday, height, weight, activityAmount, diabetes,
			diabetesYear, medicine, injection, diseases);
		return signUpRequest;
	}

}
