package com.coniverse.dangjang.domain.healthmetric.controller;

import static com.coniverse.dangjang.fixture.HealthMetricFixture.*;
import static com.coniverse.dangjang.support.SimpleMockMvc.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.ResultActions;

import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthConnectPostRequest;
import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthConnectRegisterRequest;
import com.coniverse.dangjang.support.ControllerTest;
import com.coniverse.dangjang.support.annotation.WithDangjangUser;

/**
 * @author TEO
 * @since 1.0.0
 */
@WithDangjangUser
class HealthConnectControllerTest extends ControllerTest {
	private static final String DATA_POST_URI = "/api/v1/health-connect";
	private static final String INTERLOCK_POST_URI = "/api/v1/health-connect/interlock";

	@Test
	void 헬스_커넥트_데이터를_등록하면_성공_메시지를_반환한다() throws Exception {
		// given
		String type = CommonCode.ETC.getTitle();
		HealthConnectPostRequest request = 헬스_커넥트_데이터_등록_요청(type, 50);
		String content = objectMapper.writeValueAsString(request);

		// when
		ResultActions resultActions = post(mockMvc, DATA_POST_URI, content);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase()),
			jsonPath("$.data").doesNotExist()
		);
	}

	@Test
	void 헬스_커넥트를_연동하면_성공_메시지를_반환한다() throws Exception {
		// given
		HealthConnectRegisterRequest request = 헬스_커넥트_연동_요청(true);
		String content = objectMapper.writeValueAsString(request);
		// when
		ResultActions resultActions = patch(mockMvc, INTERLOCK_POST_URI, content);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase()),
			jsonPath("$.data").doesNotExist()
		);
	}

}