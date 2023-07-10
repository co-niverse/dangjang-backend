package com.coniverse.dangjang.domain.intro.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.ResultActions;

import com.coniverse.dangjang.ControllerTest;
import com.coniverse.dangjang.domain.intro.dto.IntroInfo;
import com.coniverse.dangjang.domain.intro.service.IntroService;
import com.coniverse.dangjang.global.exception.BadRequestException;

/**
 * @author Teo
 * @since 1.0
 */
class IntroControllerTest extends ControllerTest {
	private final String URI = "/api/v1/intro";
	@MockBean
	private IntroService introService;

	@Test
	void 성공한_응답을_반환한다() throws Exception {
		// given
		IntroInfo introInfo = new IntroInfo("1.0.0", "1.2.0");
		given(introService.getIntroInfoV1()).willReturn(introInfo);

		// when
		ResultActions resultActions = get(URI);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase()),
			jsonPath("$.data.minVersion").value(introInfo.minVersion()),
			jsonPath("$.data.latestVersion").value(introInfo.latestVersion())
		);
	}

	@Test
	void 실패한_응답을_반환한다() throws Exception {
		// given
		given(introService.getIntroInfoV1()).willThrow(new BadRequestException());

		// when
		ResultActions resultActions = get(URI);

		// then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.errorCode").value(400),
			jsonPath("$.message").value("잘못된 요청입니다.")
		);
	}
}