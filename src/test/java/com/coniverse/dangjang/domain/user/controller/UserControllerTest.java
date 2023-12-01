package com.coniverse.dangjang.domain.user.controller;

import static com.coniverse.dangjang.support.SimpleMockMvc.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.coniverse.dangjang.support.ControllerTest;
import com.coniverse.dangjang.support.annotation.WithDangjangUser;

/**
 * @author EVE
 * @since 1.0.0
 */
class UserControllerTest extends ControllerTest {
	private static final String NICKNAME_URI = "/api/v1/user/duplicateNickname";
	private static final String MY_PAGE_URI = "/api/v1/user/mypage";
	private static final String WITHDRAWAL_URI = "/api/v1/user/withdrawal";

	@Test
	void 닉네임을_받아와_확인을_성공한다() throws Exception {
		//given
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("nickname", "hello");

		// when
		ResultActions resultActions = get(mockMvc, NICKNAME_URI, params);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase())
		);

	}

	@ParameterizedTest
	@ValueSource(strings = {"", "testtesttesttesttest"})
	void 조건에_맞지_않는_닉네임을_받아와_Bad_Request를_반환한다(String nickname) throws Exception {
		//given
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("nickname", nickname);

		// when
		ResultActions resultActions = get(mockMvc, NICKNAME_URI, params);

		// then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.message").value("올바르지 못한 데이터입니다.")
		);
	}

	@WithDangjangUser
	@Test
	void 마이페이지에_필요한_정보를_전달한다() throws Exception {
		//given
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

		// when
		ResultActions resultActions = get(mockMvc, MY_PAGE_URI, params);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase())
		);
	}

	@WithDangjangUser
	@Test
	void 회원탈퇴를_성공한다() throws Exception {
		// when
		ResultActions resultActions = delete(mockMvc, WITHDRAWAL_URI);

		// then
		resultActions.andExpectAll(
			status().isNoContent()
		);
	}

}
