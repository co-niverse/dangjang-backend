package com.coniverse.dangjang.domain.user.dto;

import java.util.Date;

public class TestDto {
	public static SignUpRequest getSignUpRequest() {
		SignUpRequest signUpRequest = new SignUpRequest();
		signUpRequest.setNickname("test");
		signUpRequest.setAccessToken("287873365589");
		signUpRequest.setGender(false);
		signUpRequest.setInjection(true);
		signUpRequest.setMedicine(true);
		signUpRequest.setProvider("kakao");
		signUpRequest.setBirthday(new Date());
		signUpRequest.setDiabetes(true);
		signUpRequest.setHeight(158);
		signUpRequest.setWeight(50);
		signUpRequest.setDiabetes_year(2);
		signUpRequest.setActivityAmount("LOW");
		return signUpRequest;
	}

	public static Object getWrongSignUpRequest() {
		Object loginResponse = new Object();

		return loginResponse;
	}
}
