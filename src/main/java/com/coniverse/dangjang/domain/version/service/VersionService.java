package com.coniverse.dangjang.domain.version.service;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.version.dto.Version;
import com.coniverse.dangjang.domain.version.dto.response.VersionResponse;

import lombok.RequiredArgsConstructor;

/**
 * @author TEO
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class VersionService {
	private static final String MIN_VERSION = Version.MINIMUM.getVersion();
	private static final String LATEST_VERSION = Version.LATEST.getVersion();

	/**
	 * @return IntroResponse
	 * @since 1.0.0
	 */
	public VersionResponse<?> getIntroResponse() {
		VersionResponse<?> versionResponse = new VersionResponse<>(MIN_VERSION, LATEST_VERSION, null);

		return versionResponse;
	}
}
