package com.coniverse.dangjang.domain.guide.exercise.controller;

import static com.coniverse.dangjang.fixture.GuideFixture.*;
import static com.coniverse.dangjang.support.SimpleMockMvc.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.coniverse.dangjang.domain.guide.exercise.dto.ExerciseGuideResponse;
import com.coniverse.dangjang.domain.guide.exercise.service.ExerciseGuideSearchService;
import com.coniverse.dangjang.support.ControllerTest;
import com.coniverse.dangjang.support.annotation.WithDangjangUser;

/**
 * @author EVE
 * @since 1.0.0
 */
@WithDangjangUser
class ExerciseGuideControllerTest extends ControllerTest {
	private static final String URL = "/api/guide/exercise";
	@Autowired
	private ExerciseGuideSearchService exerciseGuideSearchService;

	private String 조회_날짜 = "2023-12-31";
	private String 유효하지_않는_날짜 = "2023-12-35";

	private ExerciseGuideResponse 운동_가이드_응답 = 운동_가이드_응답(조회_날짜);

	@Test
	void 운동가이드_조회를_성공한다() throws Exception {
		//given
		doReturn(운동_가이드_응답).when(exerciseGuideSearchService).findGuide(any(), any());
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("date", 조회_날짜);

		//when
		ResultActions resultActions = get(mockMvc, URL, params);
		//then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value("OK"),
			jsonPath("$.data.createdAt").value(조회_날짜),
			jsonPath("$.data.needStepByTTS").value(운동_가이드_응답.needStepByTTS()),
			jsonPath("$.data.needStepByLastWeek").value(운동_가이드_응답.needStepByLastWeek()),
			jsonPath("$.data.comparedToLastWeek").value(운동_가이드_응답.comparedToLastWeek()),
			jsonPath("$.data.content").value(운동_가이드_응답.content()),
			jsonPath("$.data.stepsCount").value(운동_가이드_응답.stepsCount()),
			jsonPath("$.data.exerciseCalories").isArray()
		);
	}

	@Test
	void 유효하지_않은_날짜로_운동가이드_조회를_실패한다() throws Exception {
		//given

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("date", 유효하지_않는_날짜);

		//when
		ResultActions resultActions = get(mockMvc, URL, params);
		//then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.errorCode").value(400),
			jsonPath("$.success").value(false)
		);
	}
}
