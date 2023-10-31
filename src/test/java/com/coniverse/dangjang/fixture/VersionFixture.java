package com.coniverse.dangjang.fixture;

import com.coniverse.dangjang.domain.version.dto.request.VersionRequest;
import com.coniverse.dangjang.domain.version.dto.response.VersionResponse;
import com.coniverse.dangjang.domain.version.entity.Version;

/**
 * @author TEO
 * @since 1.3.0
 */
public class VersionFixture {
	public static Version 버전_엔티티() {
		return getVersion("1.0.0", "1.0.0");
	}

	public static Version 버전_엔티티(String minVersion, String latestVersion) {
		return getVersion(minVersion, latestVersion);
	}

	private static Version getVersion(String minVersion, String latestVersion) {
		return Version.builder()
			.minVersion(minVersion)
			.latestVersion(latestVersion)
			.build();
	}

	public static VersionRequest 버전_요청() {
		return getVersionRequest("1.0.0", "1.0.0", "1234");
	}

	public static VersionRequest 버전_요청(String minVersion, String latestVersion, String key) {
		return getVersionRequest(minVersion, latestVersion, key);
	}

	private static VersionRequest getVersionRequest(String minVersion, String latestVersion, String key) {
		return new VersionRequest(minVersion, latestVersion, key);
	}

	public static VersionResponse<?> 버전_응답() {
		return new VersionResponse<>("1.0.0", "1.0.0", null);
	}
}
