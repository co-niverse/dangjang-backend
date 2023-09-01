package com.coniverse.dangjang.domain.guide.bloodsugar.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.guide.bloodsugar.document.BloodSugarGuide;

public interface BloodSugarGuideRepository extends MongoRepository<BloodSugarGuide, String> {
	Optional<BloodSugarGuide> findByOauthIdAndCreatedAtAndType(String oauthId, LocalDate createdAt, CommonCode type);

	void deleteByOauthIdAndCreatedAtAndType(String oauthId, LocalDate createdAt, CommonCode type);
}
