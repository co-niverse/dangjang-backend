package com.coniverse.dangjang.domain.intro.controller;

import static com.coniverse.dangjang.support.SimpleMockMvc.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.ResultActions;

import com.coniverse.dangjang.ControllerTest;
import com.coniverse.dangjang.domain.intro.dto.IntroResponse;
import com.coniverse.dangjang.domain.intro.service.IntroService;
import com.coniverse.dangjang.global.exception.BadRequestException;

/**
 * @author TEO
 * @since 1.0.0
 */
class IntroControllerTest extends ControllerTest {
	private static final String URI = "/api/intro";
	@Autowired
	private IntroService introService;

	@Nested
	class Test_Intro_URL에_접근시 {
		@Test
		void 성공한_응답을_반환한다() throws Exception {
			// given
			IntroResponse introResponse = new IntroResponse<>("1.0.0", "1.2.0", null);
			given(introService.getTestIntroResponse()).willReturn(introResponse);

			// when
			ResultActions resultActions = get(mockMvc, URI + "/test");

			// then
			resultActions.andExpectAll(
				status().isOk(),
				jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase()),
				jsonPath("$.data.minVersion").value(introResponse.minVersion()),
				jsonPath("$.data.latestVersion").value(introResponse.latestVersion())
			);
		}

		@Test
		void 실패한_응답을_반환한다() throws Exception {
			// given
			given(introService.getTestIntroResponse()).willThrow(new BadRequestException());

			// when
			ResultActions resultActions = get(mockMvc, URI + "/test");

			// then
			resultActions.andExpectAll(
				status().isBadRequest(),
				jsonPath("$.errorCode").value(400),
				jsonPath("$.message").value("잘못된 요청입니다.")
			);
		}
	}
}
