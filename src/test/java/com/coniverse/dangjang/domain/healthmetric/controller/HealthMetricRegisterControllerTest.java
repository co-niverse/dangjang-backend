package com.coniverse.dangjang.domain.healthmetric.controller;

import static com.coniverse.dangjang.fixture.HealthMetricFixture.*;
import static com.coniverse.dangjang.support.SimpleMockMvc.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthMetricPatchRequest;
import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthMetricPostRequest;
import com.coniverse.dangjang.domain.healthmetric.dto.response.HealthMetricResponse;
import com.coniverse.dangjang.domain.healthmetric.service.HealthMetricRegisterService;
import com.coniverse.dangjang.support.ControllerTest;
import com.coniverse.dangjang.support.annotation.WithDangjangUser;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author TEO
 * @since 1.0.0
 */
@WithDangjangUser
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HealthMetricRegisterControllerTest extends ControllerTest {
	public static final String URL = "/api/health-metric";
	private final HealthMetricResponse response = 건강지표_등록_응답();
	private String postContent;
	private String patchContent;
	@Autowired
	private HealthMetricRegisterService healthMetricRegisterService;

	@BeforeAll
	void setUp() throws JsonProcessingException {
		postContent = objectMapper.writeValueAsString(건강지표_등록_요청());
		patchContent = objectMapper.writeValueAsString(단위_변경한_건강지표_수정_요청());
	}

	@Order(100)
	@Test
	void 건강지표를_등록하면_성공_메시지를_반환한다() throws Exception {
		// given
		given(healthMetricRegisterService.register(any(), anyString())).willReturn(response);

		// when
		ResultActions resultActions = post(mockMvc, URL, postContent);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase()),
			jsonPath("$.data.createdAt").value(response.createdAt().toString()),
			jsonPath("$.data.type").value(response.type()),
			jsonPath("$.data.unit").value(response.unit()),
			jsonPath("$.data.guide.unit").doesNotExist(),
			jsonPath("$.data.guide.type").value(response.guide().type()),
			jsonPath("$.data.guide.content").value(response.guide().content())
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

	@Order(310)
	@ParameterizedTest
	@ValueSource(strings = {"-1", "숫자", "1..", "number"})
	void 건강지표_등록_RequestBody의_unit이_유효하지_않으면_예외가_발생한다(String unit) throws Exception {
		// given
		HealthMetricPostRequest request = new HealthMetricPostRequest("아침 식전", "2023-12-31", unit);
		String content = objectMapper.writeValueAsString(request);

		// when
		ResultActions resultActions = post(mockMvc, URL, content);

		// then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.errorCode").value(400),
			jsonPath("$.fieldErrors[0].field").value("unit"),
			jsonPath("$.fieldErrors[0].rejectedValue").value(unit)
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
		given(healthMetricRegisterService.update(any(), anyString())).willReturn(response);

		// when
		ResultActions resultActions = patch(mockMvc, URL, patchContent);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase()),
			jsonPath("$.data.createdAt").value(response.createdAt().toString()),
			jsonPath("$.data.type").value(response.type()),
			jsonPath("$.data.unit").value(response.unit()),
			jsonPath("$.data.guide.unit").doesNotExist(),
			jsonPath("$.data.guide.type").value(response.guide().type()),
			jsonPath("$.data.guide.content").value(response.guide().content())
		);
	}

	@Order(510)
	@Test
	void 이전_타입과_같은_타입으로_수정하면_예외가_발생한다() throws Exception {
		// given
		HealthMetricPatchRequest request = new HealthMetricPatchRequest("점심식후", "점심식후", "2023-12-31", "100");
		String content = objectMapper.writeValueAsString(request);

		// when
		ResultActions resultActions = patch(mockMvc, URL, content);

		// then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.errorCode").value(400),
			jsonPath("$.message").value("type과 newType은 같을 수 없습니다.")
		);
	}
}
