package com.coniverse.dangjang.domain.log.controller;

import static com.coniverse.dangjang.fixture.LogFixture.*;
import static com.coniverse.dangjang.support.SimpleMockMvc.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.ResultActions;

import com.coniverse.dangjang.domain.log.dto.request.LogRequest;
import com.coniverse.dangjang.support.ControllerTest;
import com.coniverse.dangjang.support.annotation.WithDangjangUser;

/**
 * @author TEO
 * @since 1.0.0
 */
@WithDangjangUser
class AppLogControllerTest extends ControllerTest {
	private static final String URL = "/api/log";
	private static final String fixedEventLogName = "홈_혈당_클릭";
	private static final String fixedScreenName = "홈";
	private static final int fixedLogVersion = 1;
	private static final String fixedSessionId = "12341234";
	private static final HashMap<String, Object> fixedLogData = new HashMap<>();

	@Test
	void 로그를_등록하면_성공_메시지를_반환한다() throws Exception {
		// given
		LogRequest request = 로그_요청();
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

	@ParameterizedTest
	@ValueSource(strings = {"", " "})
	void RequestBody의_EventLogName이_유효하지_않으면_예외가_발생한다(String eventLogName) throws Exception {
		// given
		LogRequest request = new LogRequest(eventLogName, fixedScreenName, fixedLogVersion, fixedSessionId, fixedLogData);
		String content = objectMapper.writeValueAsString(request);

		// when
		ResultActions resultActions = post(mockMvc, URL, content);

		// then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.errorCode").value(400),
			jsonPath("$.fieldErrors[0].field").value("eventLogName"),
			jsonPath("$.fieldErrors[0].rejectedValue").value(eventLogName)
		);
	}

	@ParameterizedTest
	@ValueSource(strings = {"", " "})
	void RequestBody의_screenName이_유효하지_않으면_예외가_발생한다(String screenName) throws Exception {
		// given
		LogRequest request = new LogRequest(fixedEventLogName, screenName, fixedLogVersion, fixedSessionId, fixedLogData);
		String content = objectMapper.writeValueAsString(request);

		// when
		ResultActions resultActions = post(mockMvc, URL, content);

		// then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.errorCode").value(400),
			jsonPath("$.fieldErrors[0].field").value("screenName"),
			jsonPath("$.fieldErrors[0].rejectedValue").value(screenName)
		);
	}

	@ParameterizedTest
	@ValueSource(ints = {0, -1})
	void RequestBody의_logVersion이_유효하지_않으면_예외가_발생한다(int logVersion) throws Exception {
		// given
		LogRequest request = new LogRequest(fixedEventLogName, fixedScreenName, logVersion, fixedSessionId, fixedLogData);
		String content = objectMapper.writeValueAsString(request);

		// when
		ResultActions resultActions = post(mockMvc, URL, content);

		// then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.errorCode").value(400),
			jsonPath("$.fieldErrors[0].field").value("logVersion"),
			jsonPath("$.fieldErrors[0].rejectedValue").value(logVersion)
		);
	}

	@ParameterizedTest
	@ValueSource(strings = {"", " "})
	void RequestBody의_sessionId가_유효하지_않으면_예외가_발생한다(String sessionId) throws Exception {
		// given
		LogRequest request = new LogRequest(fixedEventLogName, fixedScreenName, fixedLogVersion, sessionId, fixedLogData);
		String content = objectMapper.writeValueAsString(request);

		// when
		ResultActions resultActions = post(mockMvc, URL, content);

		// then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.errorCode").value(400),
			jsonPath("$.fieldErrors[0].field").value("sessionId"),
			jsonPath("$.fieldErrors[0].rejectedValue").value(sessionId)
		);
	}
}