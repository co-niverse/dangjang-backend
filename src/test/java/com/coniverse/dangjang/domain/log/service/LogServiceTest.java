package com.coniverse.dangjang.domain.log.service;

import static com.coniverse.dangjang.fixture.LogFixture.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coniverse.dangjang.domain.log.dto.request.LogRequest;

/**
 * @author TEO
 * @since 1.0.0
 */
@SpringBootTest
class LogServiceTest {
	@Autowired
	private LogService logService;

	@Test
	void 로그_커넥션이_끊겨도_에러를_발생하지_않는다() {
		// given
		LogRequest request = 로그_요청();

		// when & then
		assertDoesNotThrow(() -> logService.sendLog(request));
	}
}