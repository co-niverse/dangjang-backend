package com.coniverse.dangjang.domain.version.controller;

import static com.coniverse.dangjang.fixture.VersionFixture.*;
import static com.coniverse.dangjang.support.SimpleMockMvc.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.ResultActions;

import com.coniverse.dangjang.domain.version.dto.request.VersionRequest;
import com.coniverse.dangjang.domain.version.dto.response.VersionResponse;
import com.coniverse.dangjang.domain.version.service.VersionService;
import com.coniverse.dangjang.support.ControllerTest;

/**
 * @author TEO
 * @since 1.3.0
 */
class VersionControllerTest extends ControllerTest {
	private static final String URI = "/api/version";

	@Autowired
	private VersionService versionService;

	@Test
	void 앱_버전을_GET_요청하면_성공한_응답을_반환한다() throws Exception {
		// given
		VersionResponse<?> versionResponse = 버전_응답();
		doReturn(versionResponse).when(versionService).getVersionResponse();

		// when
		ResultActions resultActions = get(mockMvc, URI + "/intro");

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase()),
			jsonPath("$.data.minVersion").value(versionResponse.minVersion()),
			jsonPath("$.data.latestVersion").value(versionResponse.latestVersion())
		);
	}

	@Test
	void 앱_버전을_POST_요청하면_성공한_응답을_반환한다() throws Exception {
		// given
		VersionRequest versionRequest = 버전_요청();
		String content = objectMapper.writeValueAsString(versionRequest);
		VersionResponse<?> versionResponse = 버전_응답();
		doReturn(versionResponse).when(versionService).saveVersion(any());

		// when
		ResultActions resultActions = post(mockMvc, URI, content);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase()),
			jsonPath("$.data.minVersion").value(versionResponse.minVersion()),
			jsonPath("$.data.latestVersion").value(versionResponse.latestVersion())
		);
	}

	@ParameterizedTest
	@ValueSource(strings = {"", " ", "1/1/1", "1.1.0 ", " 1.0.1"})
	void RequestBody의_minVersion이_잘못됐을_경우_실패한_응답을_반환한다(String minVersion) throws Exception {
		// given
		VersionRequest versionRequest = 버전_요청(minVersion, "1.0.1", "1234");
		String content = objectMapper.writeValueAsString(versionRequest);

		// when
		ResultActions resultActions = post(mockMvc, URI, content);

		// then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.errorCode").value(400),
			jsonPath("$.fieldErrors[0].field").value("minVersion"),
			jsonPath("$.fieldErrors[0].rejectedValue").value(minVersion)
		);
	}

	@ParameterizedTest
	@ValueSource(strings = {"", " ", "1/1/1", "1.1.0 ", " 1.0.1"})
	void RequestBody의_latestVersion이_잘못됐을_경우_실패한_응답을_반환한다(String latestVersion) throws Exception {
		// given
		VersionRequest versionRequest = 버전_요청("1.0.1", latestVersion, "1234");
		String content = objectMapper.writeValueAsString(versionRequest);

		// when
		ResultActions resultActions = post(mockMvc, URI, content);

		// then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.errorCode").value(400),
			jsonPath("$.fieldErrors[0].field").value("latestVersion"),
			jsonPath("$.fieldErrors[0].rejectedValue").value(latestVersion)
		);
	}

	@ParameterizedTest
	@ValueSource(strings = {"", " ", "\n"})
	void RequestBody의_key가_잘못됐을_경우_실패한_응답을_반환한다(String key) throws Exception {
		// given
		VersionRequest versionRequest = 버전_요청("1.0.1", "1.0.1", key);
		String content = objectMapper.writeValueAsString(versionRequest);

		// when
		ResultActions resultActions = post(mockMvc, URI, content);

		// then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.errorCode").value(400),
			jsonPath("$.fieldErrors[0].field").value("key"),
			jsonPath("$.fieldErrors[0].rejectedValue").value(key)
		);
	}
}
