package com.coniverse.dangjang.domain.guide.exercise.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.coniverse.dangjang.domain.guide.exercise.document.ExerciseGuide;

public interface ExerciseGuideRepository extends MongoRepository<ExerciseGuide, String> {
	@Query("{'oauthId': ?0, 'createdAt': ?1}")
	Optional<ExerciseGuide> findByOauthIdAndCreatedAt(String oauthId, LocalDate createdAt);
}
