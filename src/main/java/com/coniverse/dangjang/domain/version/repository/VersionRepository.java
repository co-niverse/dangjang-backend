package com.coniverse.dangjang.domain.version.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coniverse.dangjang.domain.version.entity.Version;

public interface VersionRepository extends JpaRepository<Version, Long> {
}
