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

	/**
	 * 체중 가이드 조회
	 *
	 * @param oauthId   사용자 ID
	 * @param createdAt 생성일
	 * @return 체중 가이드
	 * @since 1.0.0
	 */
	WeightGuide findByOauthIdAndCreatedAt(String oauthId, LocalDate createdAt);
}
