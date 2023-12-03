package com.coniverse.dangjang.domain.guide.common.controller;

import static com.coniverse.dangjang.fixture.GuideFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;
import static com.coniverse.dangjang.support.SimpleMockMvc.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.coniverse.dangjang.domain.guide.common.dto.response.DayGuideResponse;
import com.coniverse.dangjang.domain.guide.common.service.DayGuideService;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.support.ControllerTest;
import com.coniverse.dangjang.support.annotation.WithDangjangUser;

/**
 * @author EVE
 * @since 1.0.0
 */
@WithDangjangUser
class DayGuideControllerTest extends ControllerTest {
	@Autowired
	private DayGuideService dayGuideService;
	private static final String URI = "/api/v1/guide";
	private User user = 유저_테오();
	private String 테오_닉네임 = user.getNickname();
	private String 조회_날짜 = "2023-12-31";
	private LocalDate 조회_날짜_Date = LocalDate.parse(조회_날짜);
	private String 유효하지_않은_날짜 = "2023-12-35";
	private DayGuideResponse 조회_응답 = 하루_가이드_응답(user, 조회_날짜_Date, 혈당_오늘의_가이드(조회_날짜), 체중_하루_가이드(), 운동_하루_가이드());

	@Test
	void 하루_가이드를_조회하면_성공을_반환한다() throws Exception {
		//given
		doReturn(조회_응답).when(dayGuideService).getDayGuide(any(), any());

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("date", 조회_날짜);
		//when
		ResultActions resultActions = get(mockMvc, URI, params);
		//then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.data.nickname").value(테오_닉네임),
			jsonPath("$.data.date").value(조회_날짜),
			jsonPath("$.data.bloodSugars").isNotEmpty(),
			jsonPath("$.data.weight.unit").value(조회_응답.weight().unit()),
			jsonPath("$.data.exercise.stepCount").value(조회_응답.exercise().stepCount())
		);
	}

	@Test
	void 유효하지_않는_날짜로_하루_가이드를_조회하면_400을_반환한다() throws Exception {
		//given

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("date", 유효하지_않은_날짜);
		//when
		ResultActions resultActions = get(mockMvc, URI, params);
		//then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.errorCode").value(400)
		);
	}
}
