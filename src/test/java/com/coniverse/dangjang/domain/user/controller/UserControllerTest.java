package com.coniverse.dangjang.domain.user.controller;

import static com.coniverse.dangjang.support.SimpleMockMvc.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.coniverse.dangjang.support.ControllerTest;

/**
 * @author EVE
 * @since 1.0.0
 */
public class UserControllerTest extends ControllerTest {
	private final String URI = "/api";

	@Test
	void 닉네임을_받아와_확인을_성공한다() throws Exception {
		//given
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("nickname", "hello");
		// when
		ResultActions resultActions = get(mockMvc, URI + "/duplicateNickname", params);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase())
		);

	}

	@Test
	void _8글자이상인_닉네임을_받아와_Bad_Request를_반환한다() throws Exception {
		//given
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("nickname", "fjdksslkfjdskf");
		// when
		ResultActions resultActions = get(mockMvc, URI + "/duplicateNickname", params);

		// then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.message").value("올바르지 못한 데이터입니다.")
		);
	}

	@Test
	void 숫자가_포함된_닉네임을_받아와_Bad_Request를_반환한다() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("nickname", "dfj83");
		// when
		ResultActions resultActions = get(mockMvc, URI + "/duplicateNickname", params);

		// then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.message").value("올바르지 못한 데이터입니다.")
		);

	}

	@Test
	void 비어있는_닉네임은_Bad_Request를_반환한다() throws Exception {
		// given
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("nickname", "");
		// when
		ResultActions resultActions = get(mockMvc, URI + "/duplicateNickname", params);

		// then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.message").value("올바르지 못한 데이터입니다.")
		);

	}

}
