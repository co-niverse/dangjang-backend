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
	public static final String URL = "/api/health-metric";
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
		given(healthMetricRegistrationService.register(any(), anyString())).willReturn(response);

		// when
		ResultActions resultActions = post(mockMvc, URL, postContent);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase()),
			jsonPath("$.data.createdAt").value(response.createdAt().toString()),
			jsonPath("$.data.type").value(response.type()),
			jsonPath("$.data.unit").value(response.unit())
		);
	}

	@Order(200)
	@Test
	void 건강지표_등록_RequestBody의_건강지표_타입이_비어있으면_예외가_발생한다() throws Exception {
		// given
		HealthMetricPostRequest request = new HealthMetricPostRequest("", "2023-12-31", "100");
		String content = objectMapper.writeValueAsString(request);

		// when
		ResultActions resultActions = post(mockMvc, URL, content);

		// then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.errorCode").value(400),
			jsonPath("$.fieldErrors[0].field").value("type"),
			jsonPath("$.fieldErrors[0].rejectedValue").value("")
		);
	}

	@Order(300)
	@Test
	void 건강지표_등록_RequestBody의_unit이_비어있으면_예외가_발생한다() throws Exception {
		// given
		HealthMetricPostRequest request = new HealthMetricPostRequest("아침 식전", "2023-12-31", " ");
		String content = objectMapper.writeValueAsString(request);

		// when
		ResultActions resultActions = post(mockMvc, URL, content);

		// then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.errorCode").value(400),
			jsonPath("$.fieldErrors[0].field").value("unit"),
			jsonPath("$.fieldErrors[0].rejectedValue").value(" ")
		);
	}

	@Order(400)
	@ParameterizedTest
	@ValueSource(strings = {"2023-02-29", "2023-04-31", "2023-06-31", "2023-09-31", "2023-11-31", "2023.01.01", "2023/01/01"})
	void 건강지표_등록_RequestBody의_날짜가_유효하지_않으면_예외가_발생한다(String createdAt) throws Exception {
		// given
		HealthMetricPostRequest request = new HealthMetricPostRequest("아침 식전", createdAt, "100");
		String content = objectMapper.writeValueAsString(request);

		// when
		ResultActions resultActions = post(mockMvc, URL, content);

		// then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.errorCode").value(400),
			jsonPath("$.fieldErrors[0].field").value("createdAt"),
			jsonPath("$.fieldErrors[0].rejectedValue").value(createdAt)
		);
	}

	@Order(500)
	@Test
	void 건강지표를_수정하면_성공_메시지를_반환한다() throws Exception {
		// given
		given(healthMetricRegistrationService.update(any(), anyString())).willReturn(response);

		// when
		ResultActions resultActions = patch(mockMvc, URL, patchContent);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase()),
			jsonPath("$.data.createdAt").value(response.createdAt().toString())
		);
	}
}
