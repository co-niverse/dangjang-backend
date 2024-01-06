package com.coniverse.dangjang.domain.notification.controller;

import static com.coniverse.dangjang.fixture.NotificationFixture.*;
import static com.coniverse.dangjang.support.SimpleMockMvc.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import com.coniverse.dangjang.domain.notification.dto.request.CheckNotificationIdRequest;
import com.coniverse.dangjang.domain.notification.dto.response.NotificationResponse;
import com.coniverse.dangjang.domain.notification.service.NotificationService;
import com.coniverse.dangjang.support.ControllerTest;
import com.coniverse.dangjang.support.annotation.WithDangjangUser;

/**
 * NotificationControllerTest
 *
 * @author EVE
 * @since 1.1.0
 */
@WithDangjangUser
class NotificationControllerTest extends ControllerTest {
	@Autowired
	private NotificationService notificationService;
	private static final String URI = "/api/v1/notification";

	@Test
	void 사용자의_알림목록을_조회하면_성공메세지를_반환한다() throws Exception {
		//given
		List<NotificationResponse> response = 사용자_알림_목록();
		given(notificationService.getNotificationList(any())).willReturn(response);
		//when
		ResultActions resultActions = get(mockMvc, URI);
		//then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.data.notificationResponseList[0].notificationId").value(response.get(0).notificationId()),
			jsonPath("$.data.notificationResponseList[0].title").value(response.get(0).title()),
			jsonPath("$.data.notificationResponseList[0].content").value(response.get(0).content()),
			jsonPath("$.data.notificationResponseList[0].type").value(response.get(0).type()),
			jsonPath("$.data.notificationResponseList[0].createdAt").value(response.get(0).createdAt().toString())
		);

	}

	@Test
	void 사용자가_알림확인_여부_변경_요청하면_성공메세지를_반환한다() throws Exception {
		//given
		CheckNotificationIdRequest request = 사용자_알림_확인요청_목록();
		String content = objectMapper.writeValueAsString(request);
		//when
		ResultActions resultActions = patch(mockMvc, URI, content);
		//then
		resultActions.andExpectAll(
			status().isOk()
		);
	}

	@Test
	void 사용자가_알림확인_변경_요청중_request가_Empty일때_400예외를_반환한다() throws Exception {
		//given
		CheckNotificationIdRequest request = new CheckNotificationIdRequest(List.of());
		String content = objectMapper.writeValueAsString(request);
		//when
		ResultActions resultActions = patch(mockMvc, URI, content);
		//then
		resultActions.andExpectAll(
			status().isBadRequest()
		);
	}
}
