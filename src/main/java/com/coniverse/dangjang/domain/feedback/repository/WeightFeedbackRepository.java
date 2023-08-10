package com.coniverse.dangjang.domain.feedback.repository;

import java.time.LocalDate;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.coniverse.dangjang.domain.analysis.document.WeightFeedback;

public interface WeightFeedbackRepository extends MongoRepository<WeightFeedback, String> {
	@Query("{'oauthId': ?0, 'createdAt': ?1}")
	WeightFeedback findByFeedbackId(String oauthId, LocalDate createdAt);

	@Query(value = "{'oauthId': ?0, 'createdAt': ?1}", delete = true)
	Long deleteByFeedbackId(String oauthId, LocalDate createdAt);
}
