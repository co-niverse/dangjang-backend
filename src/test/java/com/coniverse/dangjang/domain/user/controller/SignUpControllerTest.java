package com.coniverse.dangjang.domain.user.controller;

import static com.coniverse.dangjang.support.SimpleMockMvc.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.ResultActions;

import com.coniverse.dangjang.domain.auth.dto.Response.LoginResponse;
import com.coniverse.dangjang.domain.user.dto.SignUpRequest;
import com.coniverse.dangjang.domain.user.dto.TestDto;
import com.coniverse.dangjang.domain.user.service.UserService;
import com.coniverse.dangjang.support.ControllerTest;

/**
 * @author EVE
 * @since 1.0.0
 */
public class SignUpControllerTest extends ControllerTest {
	private final String URI = "/api/signUp";
	@Autowired
	private UserService userService;

	@Test
	void 회원가입_성공한다() throws Exception {
		SignUpRequest signUpRequest = TestDto.getSignUpRequest();
		LoginResponse loginResponse = new LoginResponse("test", "accessToken", "refreshToken", false, false);
		given(userService.signUp(any())).willReturn(loginResponse);
		String content = objectMapper.writeValueAsString(signUpRequest);
		// when

		ResultActions resultActions = post(mockMvc, URI, content);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase())
		);

	}

}
