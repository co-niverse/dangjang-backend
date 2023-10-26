package com.coniverse.dangjang.domain.version.dto.response;

import java.util.List;

import com.coniverse.dangjang.domain.version.entity.Version;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 버전 정보를 반환하는 dto이다.
 *
 * @author TEO
 * @since 1.3.0
 */
public record VersionResponse<T>(String minVersion, String latestVersion, @JsonInclude(JsonInclude.Include.NON_NULL) List<T> loadData) {
	public VersionResponse(Version version, List<T> loadData) {
		this(version.getMinVersion(), version.getLatestVersion(), loadData);
	}
}
