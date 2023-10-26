package com.coniverse.dangjang.domain.version.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coniverse.dangjang.domain.version.entity.Version;

/**
 * version repository
 *
 * @author TEO
 * @since 1.3.0
 */
public interface VersionRepository extends JpaRepository<Version, Long> {

	/**
	 * 가장 최근의 버전 정보를 반환한다.
	 *
	 * @return Version
	 * @since 1.3.0
	 */
	Version findFirstByOrderByCreatedAtDesc();
}
