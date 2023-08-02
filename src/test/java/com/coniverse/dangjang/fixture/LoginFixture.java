package com.coniverse.dangjang.fixture;

import com.coniverse.dangjang.domain.auth.dto.request.KakaoLoginRequest;
import com.coniverse.dangjang.domain.auth.dto.request.NaverLoginRequest;
import com.coniverse.dangjang.domain.auth.dto.response.signInResponse;

/**
 * login fixture
 *
 * @author TEO
 * @since 1.0.0
 */
public class LoginFixture {
	public static final String 액세스_토큰 = "액세스_토큰";

	public static KakaoLoginRequest 카카오_로그인_요청() {
		return new KakaoLoginRequest(액세스_토큰);
	}

	public static NaverLoginRequest 네이버_로그인_요청() {
		return new NaverLoginRequest(액세스_토큰);
	}

	public static signInResponse 로그인_응답() {
		return new signInResponse("accessToken", "refreshToken", "EVE", false, false);
	}
}
