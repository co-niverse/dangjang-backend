package com.coniverse.dangjang.domain.feedback.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.coniverse.dangjang.domain.feedback.document.ExerciseGuide;

public interface ExerciseGuideRepository extends MongoRepository<ExerciseGuide, String> {
}
