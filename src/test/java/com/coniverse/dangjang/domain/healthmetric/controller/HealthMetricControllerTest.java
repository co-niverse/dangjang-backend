package com.coniverse.dangjang.domain.healthmetric.controller;

import static com.coniverse.dangjang.fixture.HealthMetricChartFixture.*;
import static com.coniverse.dangjang.fixture.HealthMetricFixture.*;
import static com.coniverse.dangjang.support.SimpleMockMvc.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.List;

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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.coniverse.dangjang.domain.healthmetric.dto.HealthMetricLastDateResponse;
import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthMetricPatchRequest;
import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthMetricPostRequest;
import com.coniverse.dangjang.domain.healthmetric.dto.response.BloodSugarMinMax;
import com.coniverse.dangjang.domain.healthmetric.dto.response.HealthMetricChartData;
import com.coniverse.dangjang.domain.healthmetric.dto.response.HealthMetricResponse;
import com.coniverse.dangjang.domain.healthmetric.service.HealthMetricChartSearchService;
import com.coniverse.dangjang.domain.healthmetric.service.HealthMetricRegisterService;
import com.coniverse.dangjang.domain.healthmetric.service.HealthMetricSearchService;
import com.coniverse.dangjang.support.ControllerTest;
import com.coniverse.dangjang.support.annotation.WithDangjangUser;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author TEO, EVE
 * @since 1.0.0
 */
@WithDangjangUser
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HealthMetricControllerTest extends ControllerTest {
	private static final String URI = "/api/v1/health-metric";
	private static LocalDate 시작_날짜 = LocalDate.parse("2023-12-31");
	private static LocalDate 마지막_날짜 = LocalDate.parse("2024-01-06");
	private static LocalDate 생성_날짜 = LocalDate.of(2023, 12, 31);
	private static List<BloodSugarMinMax> 혈당차트 = 혈당차트_생성(생성_날짜, 100, 200);
	private static List<HealthMetricChartData> 체중차트 = 체중차트_생성(생성_날짜, 100);
	private static List<HealthMetricChartData> 걸음수차트 = 걸음수차트_생성(생성_날짜, 10000);
	private static List<HealthMetricChartData> 칼로리차트 = 칼로리차트_생성(생성_날짜, 400);
	private final HealthMetricResponse response = 건강지표_등록_응답();
	private String postContent;
	private String patchContent;
	@Autowired
	private HealthMetricRegisterService healthMetricRegisterService;
	@Autowired
	private HealthMetricChartSearchService healthMetricChartSearchService;
	@Autowired
	private HealthMetricSearchService healthMetricSearchService;

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
		ResultActions resultActions = post(mockMvc, URI, postContent);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase()),
			jsonPath("$.data.createdAt").value(response.createdAt().toString()),
			jsonPath("$.data.type").value(response.type()),
			jsonPath("$.data.unit").value(response.unit()),
			jsonPath("$.data.guide.unit").doesNotExist(),
			jsonPath("$.data.guide.type").value(response.guide().type()),
			jsonPath("$.data.guide.title").value(response.guide().title()),
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
		ResultActions resultActions = post(mockMvc, URI, content);

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
		ResultActions resultActions = post(mockMvc, URI, content);

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
		ResultActions resultActions = post(mockMvc, URI, content);

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
	@ValueSource(strings = {"2023-02-29", "2023-04-31", "2023-06-31", "2023-09-31", "2023-11-31", "2023.01.01", "2023/01/01", "2023-1-1"})
	void 건강지표_등록_RequestBody의_날짜가_유효하지_않으면_예외가_발생한다(String createdAt) throws Exception {
		// given
		HealthMetricPostRequest request = new HealthMetricPostRequest("아침 식전", createdAt, "100");
		String content = objectMapper.writeValueAsString(request);

		// when
		ResultActions resultActions = post(mockMvc, URI, content);

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
		ResultActions resultActions = patch(mockMvc, URI, patchContent);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase()),
			jsonPath("$.data.createdAt").value(response.createdAt().toString()),
			jsonPath("$.data.type").value(response.type()),
			jsonPath("$.data.unit").value(response.unit()),
			jsonPath("$.data.guide.unit").doesNotExist(),
			jsonPath("$.data.guide.type").value(response.guide().type()),
			jsonPath("$.data.guide.title").value(response.guide().title()),
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
		ResultActions resultActions = patch(mockMvc, URI, content);

		// then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.errorCode").value(400),
			jsonPath("$.message").value("type과 newType은 같을 수 없습니다.")
		);
	}

	@Order(600)
	@Test
	void 건강지표_차트_데이터를_조회하면_성공을_반한환다() throws Exception {
		//given
		doReturn(건강지표_차트_응답_생성(시작_날짜, 마지막_날짜, 혈당차트, 체중차트, 걸음수차트, 칼로리차트)).when(healthMetricChartSearchService).findHealthMetricChart(any(), any(), any());
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("startDate", 시작_날짜.toString());
		params.add("endDate", 마지막_날짜.toString());

		//when
		ResultActions resultActions = get(mockMvc, URI, params);

		//then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value("OK"),
			jsonPath("$.data.bloodSugars").isArray(),
			jsonPath("$.data.bloodSugars[0].minUnit").isNumber(),
			jsonPath("$.data.weights[0].unit").isNumber(),
			jsonPath("$.data.stepCounts[0].unit").isNumber(),
			jsonPath("$.data.exerciseCalories[0].unit").isNumber()
		);
	}

	@Order(600)
	@Test
	void 유효하지_않는_날짜로_차트를_조회하면_400에러를_반환한다() throws Exception {
		//given
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("startDate", "2021-01-51");
		params.add("endDate", 마지막_날짜.toString());

		//when
		ResultActions resultActions = get(mockMvc, URI, params);

		//then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.errorCode").value(400)
		);
	}

	@Order(700)
	@Test
	void 건강지표_마지막_생성일을_전달한다() throws Exception {
		//given
		String subUrl = URI + "/last-date";
		LocalDate lastDate = LocalDate.now();
		HealthMetricLastDateResponse response = new HealthMetricLastDateResponse(lastDate);
		given(healthMetricSearchService.findHealthMetricLastDate(any())).willReturn(response);

		//when
		ResultActions resultActions = get(mockMvc, subUrl);

		//then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value("OK"),
			jsonPath("$.data.date").value(response.date().toString())
		);
	}

	@Test
	void 건강지표를_성공적으로_삭제한다() throws Exception {
		// given
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("date", "2021-01-01");
		params.add("type", "아침식전");

		// when
		ResultActions resultActions = delete(mockMvc, URI, params);

		// then
		resultActions.andExpectAll(
			status().isNoContent()
		);
	}

	@ParameterizedTest
	@ValueSource(strings = {"2021-01-51", "2021-02-29", "2021-04-31", "2021-06-31", "2021-09-31", "2021-11-31", "2021.01.01", "2021/01/01", "2021-1-1"})
	void 잘못된_날짜_parameter를_전달할_경우_400에러를_반환한다(String date) throws Exception {
		// given
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("date", date);
		params.add("type", "아침식전");

		// when
		ResultActions resultActions = delete(mockMvc, URI, params);

		// then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.errorCode").value(400),
			jsonPath("$.violationErrors[0].rejectedValue").value(date)
		);
	}

	@ParameterizedTest
	@ValueSource(strings = {" ", ""})
	void 잘못된_타입_parameter를_전달할_경우_400에러를_반환한다(String type) throws Exception {
		// given
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("date", "2021-01-01");
		params.add("type", type);

		// when
		ResultActions resultActions = delete(mockMvc, URI, params);

		// then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.errorCode").value(400),
			jsonPath("$.violationErrors[0].rejectedValue").value(type)
		);
	}
}
