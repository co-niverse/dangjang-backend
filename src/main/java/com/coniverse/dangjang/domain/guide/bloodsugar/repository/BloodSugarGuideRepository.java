package com.coniverse.dangjang.domain.guide.bloodsugar.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.coniverse.dangjang.domain.guide.bloodsugar.document.BloodSugarGuide;

/**
 * 혈당 가이드 Repository
 *
 * @author TEO
 * @since 1.0.0
 */
public interface BloodSugarGuideRepository extends MongoRepository<BloodSugarGuide, String> {
	Optional<BloodSugarGuide> findByOauthIdAndCreatedAt(String oauthId, String createdAt);
}
