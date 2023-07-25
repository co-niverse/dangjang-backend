package com.coniverse.dangjang.domain.user.dto;

import java.time.LocalDate;

/**
 * 테스트에서 필요한 Request를 반환해주는 메소드를 가진 클래스
 *
 * @author EVE
 * @since 1.0.0
 */
public class TestRequestMethod {
	public static SignUpRequest getSignUpRequest() {
		SignUpRequest signUpRequest = new SignUpRequest();
		signUpRequest.setNickname("test");
		signUpRequest.setAccessToken("287873365589");
		signUpRequest.setGender(false);
		signUpRequest.setInjection(true);
		signUpRequest.setMedicine(true);
		signUpRequest.setProvider("kakao");
		signUpRequest.setBirthday(LocalDate.now());
		signUpRequest.setDiabetes(true);
		signUpRequest.setHeight(158);
		signUpRequest.setWeight(50);
		signUpRequest.setDiabetes_year(2);
		signUpRequest.setActivityAmount("LOW");
		return signUpRequest;
	}

}
