package com.coniverse.dangjang.domain.feedback.repository;

import java.time.LocalDate;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.coniverse.dangjang.domain.feedback.document.WeightFeedback;

/**
 * 체중 FeedbackRepository
 *
 * @author EVE
 * @since 1.0.0
 */
public interface WeightFeedbackRepository extends MongoRepository<WeightFeedback, String> {
	@Query("{'oauthId': ?0, 'createdAt': ?1}")
	WeightFeedback findByFeedbackId(String oauthId, LocalDate createdAt);
}
