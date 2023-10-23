package com.coniverse.dangjang.fixture;

import java.util.Map;

import com.coniverse.dangjang.domain.log.dto.request.LogRequest;

/**
 * log fixture
 *
 * @author TEO
 * @since 1.0.0
 */
public class LogFixture {
	private static final String EVENT_LOG_NAME = "home_bloodsugar_click";
	private static final String SCREEN_NAME = "home";
	private static final int LOG_VERSION = 1;
	private static final String APP_VERSION = "1.1.0";
	private static final String SESSION_ID = "12341234";
	private static final Map<String, Object> LOG_DATA = Map.of("clicked", true, "stayTime", 1000);

	public static LogRequest 로그_요청() {
		return new LogRequest(EVENT_LOG_NAME, SCREEN_NAME, LOG_VERSION, APP_VERSION, SESSION_ID, LOG_DATA);
	}

	public static LogRequest 로그_요청_이벤트_로그명(String eventLogName) {
		return new LogRequest(eventLogName, SCREEN_NAME, LOG_VERSION, APP_VERSION, SESSION_ID, LOG_DATA);
	}

	public static LogRequest 로그_요청_스크린명(String screenName) {
		return new LogRequest(EVENT_LOG_NAME, screenName, LOG_VERSION, APP_VERSION, SESSION_ID, LOG_DATA);
	}

	public static LogRequest 로그_요청_로그_버전(int logVersion) {
		return new LogRequest(EVENT_LOG_NAME, SCREEN_NAME, logVersion, APP_VERSION, SESSION_ID, LOG_DATA);
	}

	public static LogRequest 로그_요청_앱_버전(String appVersion) {
		return new LogRequest(EVENT_LOG_NAME, SCREEN_NAME, LOG_VERSION, appVersion, SESSION_ID, LOG_DATA);
	}

	public static LogRequest 로그_요청_세션_아이디(String sessionId) {
		return new LogRequest(EVENT_LOG_NAME, SCREEN_NAME, LOG_VERSION, APP_VERSION, sessionId, LOG_DATA);
	}
}
