package com.coniverse.dangjang.domain.healthmetric.controller;

import static com.coniverse.dangjang.fixture.HealthMetricFixture.*;
import static com.coniverse.dangjang.support.SimpleMockMvc.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.ResultActions;

import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthConnectPostRequest;
import com.coniverse.dangjang.support.ControllerTest;
import com.coniverse.dangjang.support.annotation.WithDangjangUser;

@WithDangjangUser
class HealthConnectControllerTest extends ControllerTest {
	public static final String URL = "/api/health-connect";

	@Test
	void 헬스_커넥트_데이터를_등록하면_성공_메시지를_반환한다() throws Exception {
		// given
		String type = CommonCode.ETC.getTitle();
		HealthConnectPostRequest request = 헬스_커넥트_등록_요청(type, 50);
		String content = objectMapper.writeValueAsString(request);

		// when
		ResultActions resultActions = post(mockMvc, URL, content);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase()),
			jsonPath("$.data").doesNotExist()
		);
	}
}