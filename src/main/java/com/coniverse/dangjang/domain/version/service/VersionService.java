package com.coniverse.dangjang.domain.version.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coniverse.dangjang.domain.version.dto.request.VersionRequest;
import com.coniverse.dangjang.domain.version.dto.response.VersionResponse;
import com.coniverse.dangjang.domain.version.entity.Version;
import com.coniverse.dangjang.domain.version.repository.VersionRepository;

import lombok.RequiredArgsConstructor;

/**
 * 앱 버전 관련 service
 *
 * @author TEO
 * @since 1.3.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class VersionService {
	private final VersionRepository versionRepository;
	@Value("${version.key}")
	private String serverKey;

	/**
	 * 가장 최근의 버전 정보를 반환한다.
	 *
	 * @return VersionResponse
	 * @since 1.0.0
	 */
	@Transactional(readOnly = true)
	public VersionResponse<?> getVersionResponse() {
		Version version = versionRepository.findFirstByOrderByCreatedAtDesc();
		return new VersionResponse<>(version, null);
	}

	/**
	 * 버전 정보를 저장한다.
	 *
	 * @param request 버전 정보
	 * @return VersionResponse
	 * @since 1.3.0
	 */
	public VersionResponse<?> saveVersion(VersionRequest request) {
		validateKey(request.key());
		Version version = Version.builder()
			.minVersion(request.minVersion())
			.latestVersion(request.latestVersion())
			.build();
		versionRepository.save(version);
		return new VersionResponse<>(version, null);
	}

	/**
	 * 버전 정보의 키 값을 확인한다.
	 *
	 * @param key 버전 키
	 * @throws IllegalArgumentException 서버 키가 아닐 경우
	 * @since 1.3.0
	 */
	private void validateKey(String key) {
		if (!key.equals(serverKey)) {
			throw new IllegalArgumentException("올바르지 않은 키 값입니다.");
		}
	}
}
