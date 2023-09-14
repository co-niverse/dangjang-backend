package com.coniverse.dangjang.domain.guide.exercise.controller;

import static com.coniverse.dangjang.support.SimpleMockMvc.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.guide.exercise.dto.ExerciseCalorie;
import com.coniverse.dangjang.domain.guide.exercise.dto.ExerciseGuideResponse;
import com.coniverse.dangjang.domain.guide.exercise.service.ExerciseGuideSearchService;
import com.coniverse.dangjang.support.ControllerTest;
import com.coniverse.dangjang.support.annotation.WithDangjangUser;

@WithDangjangUser
class ExerciseGuideControllerTest extends ControllerTest {
	private static final String URL = "/api/guide/exercise";
	@Autowired
	private ExerciseGuideSearchService exerciseGuideSearchService;

	private String 조회_날짜 = "2023-12-31";
	private String 유효하지_않는_날짜 = "2023-12-35";

	@Test
	void 운동가이드_조회를_성공한다() throws Exception {
		//given
		int needStepByTTS = 0, needStepByWeek = 0;
		String comparedToLastWeek = "저번주대비 걸음수 가이드입니다.";
		String content = "만보대비 가이드입니다.";
		int stepsCount = 0;
		List<ExerciseCalorie> exerciseCalories = List.of(new ExerciseCalorie(CommonCode.HEALTH, 100, 60), new ExerciseCalorie(CommonCode.RUN, 200, 120));
		ExerciseGuideResponse exerciseGuideResponse = new ExerciseGuideResponse(조회_날짜, needStepByTTS, needStepByWeek, comparedToLastWeek, content, stepsCount,
			exerciseCalories);
		doReturn(exerciseGuideResponse).when(exerciseGuideSearchService).findGuide(any(), any());
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("date", 조회_날짜);

		//when
		ResultActions resultActions = get(mockMvc, URL, params);
		//then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value("OK"),
			jsonPath("$.data.createdAt").value(조회_날짜),
			jsonPath("$.data.needStepByTTS").value(needStepByTTS),
			jsonPath("$.data.needStepByLastWeek").value(needStepByWeek),
			jsonPath("$.data.comparedToLastWeek").value(comparedToLastWeek),
			jsonPath("$.data.content").value(content),
			jsonPath("$.data.stepsCount").value(stepsCount),
			jsonPath("$.data.exerciseCalories").isArray()
		);
	}

	@Test
	void 유효하지_않은_날짜로_운동가이드_조회를_실패한다() throws Exception {
		//given

		doReturn(null).when(exerciseGuideSearchService).findGuide(any(), any());
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
