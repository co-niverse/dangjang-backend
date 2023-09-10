package com.coniverse.dangjang.domain.guide.controller;

import static com.coniverse.dangjang.fixture.GuideFixture.*;
import static com.coniverse.dangjang.support.SimpleMockMvc.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.coniverse.dangjang.domain.guide.bloodsugar.dto.BloodSugarGuideResponse;
import com.coniverse.dangjang.domain.guide.bloodsugar.service.BloodSugarGuideSearchService;
import com.coniverse.dangjang.support.ControllerTest;
import com.coniverse.dangjang.support.annotation.WithDangjangUser;

/**
 * @author TEO
 * @since 1.0.0
 */
@WithDangjangUser
class BloodSugarGuideControllerTest extends ControllerTest {
	public static final String URL = "/api/guide/blood-sugar";
	private static final String createdAt = "2023-12-31";
	@Autowired
	private BloodSugarGuideSearchService bloodSugarGuideSearchService;

	@Test
	void 혈당_가이드를_조회하면_성공_메시지를_반환한다() throws Exception {
		// given
		BloodSugarGuideResponse response = 혈당_가이드_응답(createdAt);
		doReturn(response).when(bloodSugarGuideSearchService).findGuide(anyString(), anyString());

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("date", createdAt);

		// when
		ResultActions resultActions = get(mockMvc, URL, params);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase()),
			jsonPath("$.data.createdAt").value(createdAt),
			jsonPath("$.data.todayGuides").isArray(),
			jsonPath("$.data.todayGuides[0].alert").value(response.todayGuides().get(0).getAlert()),
			jsonPath("$.data.todayGuides[0].count").value(response.todayGuides().get(0).getCount()),
			jsonPath("$.data.guides").isArray(),
			jsonPath("$.data.guides[0].type").value(response.guides().get(0).type()),
			jsonPath("$.data.guides[0].alert").value(response.guides().get(0).alert()),
			jsonPath("$.data.guides[0].unit").value(response.guides().get(0).unit()),
			jsonPath("$.data.guides[0].title").value(response.guides().get(0).title()),
			jsonPath("$.data.guides[0].content").value(response.guides().get(0).content())
		);
	}

	@ParameterizedTest
	@ValueSource(strings = {"2023-12-32", "2023-13-31", "2023-02-29", "2023/01/01"})
	void 유효하지_않은_날짜로_혈당_가이드를_조회하면_400_에러를_반환한다(String createdAt) throws Exception {
		// given
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("date", createdAt);

		// when
		ResultActions resultActions = get(mockMvc, URL, params);

		// then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.errorCode").value(400),
			jsonPath("$.violationErrors[0].rejectedValue").value(createdAt)
		);
	}
}
