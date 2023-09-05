package com.coniverse.dangjang.domain.guide.weight.repository;

import java.time.LocalDate;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.coniverse.dangjang.domain.guide.weight.document.WeightGuide;

/**
 * 체중 가이드 Repository
 *
 * @author EVE
 * @since 1.0.0
 */
public interface WeightGuideRepository extends MongoRepository<WeightGuide, String> {
	WeightGuide findByOauthIdAndCreatedAt(String oauthId, LocalDate createdAt);
}
