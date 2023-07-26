package com.coniverse.dangjang.domain.user.dto;

import java.time.LocalDate;

/**
 * 테스트에서 필요한 Request를 반환해주는 메소드를 가진 클래스
 *
 * @author EVE
 * @since 1.0.0
 */
public class TestRequestMethod {
	public static SignUpRequest getSignUpRequestKakao() {
		SignUpRequest signUpRequest = new SignUpRequest("287873365589", "kakao", "test", false, LocalDate.now(), 158, 50, "LOW", true, 2, true, true, null);
		return signUpRequest;
	}

	public static SignUpRequest getSignUpRequestNaver() {
		SignUpRequest signUpRequest = new SignUpRequest("287873365589", "naver", "test", true, LocalDate.now(), 158, 50, "MEDIUM", true, 2, true, true, null);
		return signUpRequest;
	}

}
