package com.coniverse.dangjang.domain.version.service;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.version.dto.response.VersionResponse;
import com.coniverse.dangjang.domain.version.entity.Version;
import com.coniverse.dangjang.domain.version.repository.VersionRepository;

import lombok.RequiredArgsConstructor;

/**
 * @author TEO
 * @since 1.3.0
 */
@Service
@RequiredArgsConstructor
public class VersionService {
	private final VersionRepository versionRepository;

	/**
	 * 가장 최근의 버전 정보를 반환한다.
	 *
	 * @return VersionResponse
	 * @since 1.0.0
	 */
	public VersionResponse<?> getVersionResponse() {
		Version version = versionRepository.findFirstByOrderByCreatedAtDesc();
		return new VersionResponse<>(version, null);
	}
}
