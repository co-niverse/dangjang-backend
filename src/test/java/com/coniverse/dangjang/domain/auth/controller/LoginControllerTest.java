package com.coniverse.dangjang.domain.auth.controller;

import static com.coniverse.dangjang.support.SimpleMockMvc.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.ResultActions;

import com.coniverse.dangjang.support.ControllerTest;

/**
 * @author EVE
 * @since 1.0.0
 */
class LoginControllerTest extends ControllerTest {
	private final String URI = "/api/auth";

	@Test
	void 카카오_로그인_성공한다() throws Exception {

		// when
		ResultActions resultActions = post(mockMvc, URI + "/kakao",
			"{\"accessToken\" : \"accessToken123456789\"}");

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase())
		);

	}

	@Test
	void 네이버_로그인_성공한다() throws Exception {

		// when
		ResultActions resultActions = post(mockMvc, URI + "/naver",
			"{\"accessToken\" : \"accessToken123456789\"}");

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase())
		);
	}

}
