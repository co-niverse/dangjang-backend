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
	public static LogRequest 로그_요청() {
		return new LogRequest("home_bloodsugar_click", "home", 1, "12341234", Map.of("clicked", true, "stayTime", 1000));
	}
}
