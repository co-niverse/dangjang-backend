package com.coniverse.dangjang.domain.version.controller;

import static com.coniverse.dangjang.support.SimpleMockMvc.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.ResultActions;

import com.coniverse.dangjang.domain.version.dto.Version;
import com.coniverse.dangjang.domain.version.dto.response.VersionResponse;
import com.coniverse.dangjang.domain.version.service.VersionService;
import com.coniverse.dangjang.support.ControllerTest;

/**
 * @author TEO
 * @since 1.0.0
 */
class VersionControllerTest extends ControllerTest {
	private static final String URI = "/api/intro";
	private static final String MIN_VERSION = Version.MINIMUM.getVersion();
	private static final String LATEST_VERSION = Version.LATEST.getVersion();
	@Autowired
	private VersionService versionService;

	@Nested
	class Prod_Intro_URL에_접근시 {
		@Test
		void 성공한_응답을_반환한다() throws Exception {
			// given
			VersionResponse versionResponse = new VersionResponse<>(MIN_VERSION, LATEST_VERSION, null);
			given(versionService.getProdIntroResponse()).willReturn(versionResponse);

			// when
			ResultActions resultActions = get(mockMvc, URI + "/prod");

			// then
			resultActions.andExpectAll(
				status().isOk(),
				jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase()),
				jsonPath("$.data.minVersion").value(versionResponse.minVersion()),
				jsonPath("$.data.latestVersion").value(versionResponse.latestVersion())
			);
		}
	}
}
