package com.coniverse.dangjang.domain.healthmetric.controller;

import static com.coniverse.dangjang.fixture.HealthMetricFixture.*;
import static com.coniverse.dangjang.support.SimpleMockMvc.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.ResultActions;

import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthMetricPostRequest;
import com.coniverse.dangjang.domain.healthmetric.dto.response.HealthMetricResponse;
import com.coniverse.dangjang.domain.healthmetric.service.HealthMetricRegistrationService;
import com.coniverse.dangjang.support.ControllerTest;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author TEO
 * @since 1.0.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HealthMetricRegistrationControllerTest extends ControllerTest {
	public static final String URL = "/api/health-metric/{month}/{day}";
	private final HealthMetricResponse response = 건강지표_등록_응답();
	private String postContent;
	private String patchContent;
	@Autowired
	private HealthMetricRegistrationService healthMetricRegistrationService;

	private static IntStream provideMonth() {
		return IntStream.range(1, 13);
	}

	private static IntStream provideDay() {
		return IntStream.range(1, 32);
	}

	@BeforeAll
	void setUp() throws JsonProcessingException {
		postContent = objectMapper.writeValueAsString(건강지표_등록_요청());
		patchContent = objectMapper.writeValueAsString(단위_변경한_건강지표_수정_요청());
	}

	@Order(100)
	@Test
	void 건강지표를_등록하면_성공_메시지를_반환한다() throws Exception {
		// given
		given(healthMetricRegistrationService.register(any(), any(), anyString())).willReturn(response);

		// when
		ResultActions resultActions = post(mockMvc, URL, postContent, 7, 8);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase()),
			jsonPath("$.data.createdAt").value(response.createdAt().toString()),
			jsonPath("$.data.title").value(response.title()),
			jsonPath("$.data.unit").value(response.unit())
		);
	}

	@Order(200)
	@ParameterizedTest
	@ValueSource(ints = {-1, 0, 13})
	void 범위를_벗어나는_PathVariable_month를_입력하면_예외가_발생한다(int month) throws Exception {
		// when
		ResultActions resultActions = post(mockMvc, URL, postContent, month, 1);

		// then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.errorCode").value(400),
			jsonPath("$.violationErrors[0].rejectedValue").value(month)
		);
	}

	@Order(300)
	@ParameterizedTest
	@MethodSource("provideMonth")
	void 올바른_PathVariable_month를_입력하면_성공메시지를_반환한다(int month) throws Exception {
		// given
		given(healthMetricRegistrationService.register(any(), any(), anyString())).willReturn(response);

		// when
		ResultActions resultActions = post(mockMvc, URL, postContent, month, 8);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase())
		);
	}

	@Order(400)
	@ParameterizedTest
	@ValueSource(ints = {-1, 0, 32})
	void 범위를_벗어나는_PathVariable_day를_입력하면_예외가_발생한다(int day) throws Exception {
		// when
		ResultActions resultActions = post(mockMvc, URL, postContent, 1, day);

		// then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.errorCode").value(400),
			jsonPath("$.violationErrors[0].rejectedValue").value(day)
		);
	}

	@Order(500)
	@ParameterizedTest
	@MethodSource("provideDay")
	void 올바른_PathVariable_day를_입력하면_성공메시지를_반환한다(int day) throws Exception {
		// given
		given(healthMetricRegistrationService.register(any(), any(), anyString())).willReturn(response);

		// when
		ResultActions resultActions = post(mockMvc, URL, postContent, 1, day);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase())
		);
	}

	@Order(600)
	@Test
	void 건강지표_등록_RequestBody의_건강지표_타입이_비어있으면_예외가_발생한다() throws Exception {
		// given
		HealthMetricPostRequest request = new HealthMetricPostRequest("", "100");
		String content = objectMapper.writeValueAsString(request);

		// when
		ResultActions resultActions = post(mockMvc, URL, content, 7, 8);

		// then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.errorCode").value(400),
			jsonPath("$.fieldErrors[0].field").value("title"),
			jsonPath("$.fieldErrors[0].rejectedValue").value("")
		);
	}

	@Order(700)
	@Test
	void 건강지표_등록_RequestBody의_unit이_비어있으면_예외가_발생한다() throws Exception {
		// given
		HealthMetricPostRequest request = new HealthMetricPostRequest("아침 식전", " ");
		String content = objectMapper.writeValueAsString(request);

		// when
		ResultActions resultActions = post(mockMvc, URL, content, 7, 8);

		// then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.errorCode").value(400),
			jsonPath("$.fieldErrors[0].field").value("unit"),
			jsonPath("$.fieldErrors[0].rejectedValue").value(" ")
		);
	}

	@Order(800)
	@Test
	void 건강지표를_수정하면_성공_메시지를_반환한다() throws Exception {
		// given
		given(healthMetricRegistrationService.update(any(), any(), anyString())).willReturn(response);

		// when
		ResultActions resultActions = patch(mockMvc, URL, patchContent, 7, 8);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase()),
			jsonPath("$.data.createdAt").value(response.createdAt().toString())
		);
	}
}
