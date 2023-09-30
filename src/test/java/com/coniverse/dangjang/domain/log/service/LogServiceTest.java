package com.coniverse.dangjang.domain.log.service;

import static com.coniverse.dangjang.fixture.LogFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.coniverse.dangjang.domain.log.dto.request.LogRequest;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.service.UserSearchService;

/**
 * @author TEO
 * @since 1.0.0
 */
@SpringBootTest
class LogServiceTest {
	@Autowired
	private LogService logService;
	// @MockBean
	// private RestTemplate restTemplate;
	@MockBean
	private UserSearchService userSearchService;

	@Test
	void 로그_커넥션이_끊겨도_에러를_발생하지_않는다() {
		// given
		LogRequest request = 로그_요청();
		User user = 유저_테오();
		doReturn(user).when(userSearchService).findUserByOauthId(anyString());

		// when & then
		assertDoesNotThrow(() -> logService.sendLog(request, user.getOauthId()));
	}
}