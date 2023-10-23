package com.coniverse.dangjang.domain.log.mapper;

import static com.coniverse.dangjang.fixture.LogFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import com.coniverse.dangjang.domain.log.dto.app.AppLog;
import com.coniverse.dangjang.domain.log.dto.request.LogRequest;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.global.exception.EnumNonExistentException;

/**
 * @author TEO
 * @since 1.0.0
 */
class LogMapperTest {
	private final LogMapper logMapper = new LogMapperImpl();

	@Test
	void LogRequest를_AppLog로_변환한다() {
		// given
		LogRequest request = 로그_요청();
		User user = 유저_테오();

		// when
		AppLog appLog = logMapper.toAppLog(request);

		// then
		assertAll(
			() -> assertThat(appLog.event().getTitle()).isEqualTo(request.eventLogName()),
			() -> assertThat(appLog.screenName()).isEqualTo(request.screenName()),
			() -> assertThat(appLog.logVersion()).isEqualTo(request.logVersion()),
			() -> assertThat(appLog.sessionId()).isEqualTo(request.sessionId()),
			() -> assertThat(appLog.logData()).isEqualTo(request.logData())
		);
	}

	@Test
	void LogRequest의_eventLogName이_존재하지_않으면_에러를_발생한다() {
		// given
		LogRequest request = new LogRequest("nonExistentEventLogName", "screenName", 1, "1.1.0", "sessionId", new HashMap<>());
		User user = 유저_테오();

		// when & then
		assertThatThrownBy(() -> logMapper.toAppLog(request))
			.isInstanceOf(EnumNonExistentException.class);
	}
}