package com.coniverse.dangjang.domain.auth.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.ResultActions;

import com.coniverse.dangjang.ControllerTest;

/**
 * @author EVE
 * @since 1.0
 */
public class LoginControllerTest extends ControllerTest {
	private final String URI = "/api/auth";

	@Test
	void 카카오_로그인_성공() throws Exception {

		// when
		ResultActions resultActions = post(URI + "/kakao",
			"{\"accessToken\" : \"accessToken123456789\"}");

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase())
		);

	}

	@Test
	void 네이버_로그인_성공() throws Exception {

		// when
		ResultActions resultActions = post(URI + "/naver",
			"{\"accessToken\" : \"accessToken123456789\"}");

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase())
		);
	}

}
