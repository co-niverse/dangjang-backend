package com.coniverse.dangjang.domain.guide.weight.controller;

import static com.coniverse.dangjang.fixture.GuideFixture.*;
import static com.coniverse.dangjang.support.SimpleMockMvc.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.coniverse.dangjang.domain.guide.weight.dto.WeightGuideResponse;
import com.coniverse.dangjang.domain.guide.weight.service.WeightGuideSearchService;
import com.coniverse.dangjang.support.ControllerTest;
import com.coniverse.dangjang.support.annotation.WithDangjangUser;

/**
 * @author EVE
 * @since 1.0.0
 */
@WithDangjangUser
class WeightGuideControllerTest extends ControllerTest {
	@Autowired
	private WeightGuideSearchService weightGuideSearchService;
	public static final String URL = "/api/guide/weight";
	private static final String createdAt = "2023-12-31";
	private static final String 등록되지_않은_날짜 = "3000-12-33";

	@Test
	void 체중_조회를_성공한다() throws Exception {
		// given
		WeightGuideResponse response = 체중_가이드_응답(createdAt);
		doReturn(response).when(weightGuideSearchService).findGuide(any(), any());
		// when
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("date", "2023-12-31");

		// when
		ResultActions resultActions = get(mockMvc, URL, params);
		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase()),
			jsonPath("$.data.createdAt").value(createdAt),
			jsonPath("$.data.title").value(response.title()),
			jsonPath("$.data.content").value(response.content()),
			jsonPath("$.data.weightDiff").value(response.weightDiff()),
			jsonPath("$.data.bmi").value(response.bmi()),
			jsonPath("$.data.unit").value(response.unit())
		);
	}

	@Test
	void 유효하지_않는_날짜로_체중_조회를_실패한다() throws Exception {
		// given
		WeightGuideResponse response = 체중_가이드_응답(createdAt);
		doReturn(null).when(weightGuideSearchService).findGuide(any(), any());
		// when
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("date", 등록되지_않은_날짜);

		// when
		ResultActions resultActions = get(mockMvc, URL, params);
		// then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.errorCode").value(400)
		);
	}
}
