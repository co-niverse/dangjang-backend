package com.coniverse.dangjang.domain.guide.exercise.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.coniverse.dangjang.domain.guide.exercise.document.ExerciseGuide;

/**
 * 운동 가이드 Repository
 *
 * @author EVE
 * @since 1.0.0
 */
public interface ExerciseGuideRepository extends MongoRepository<ExerciseGuide, String> {

	/**
	 * 운동 가이드 조회
	 *
	 * @param oauthId   사용자 ID
	 * @param createdAt 생성일
	 * @return 운동 가이드
	 * @since 1.0.0
	 */
	Optional<ExerciseGuide> findByOauthIdAndCreatedAt(String oauthId, LocalDateTime createdAt);

	@Query(value = "{ 'oauthId' : ?0, 'createdAt' : { $gte : ?1, $lte : ?2 } }", sort = "{ 'createdAt' : 1 }")
	List<ExerciseGuide> findWeekByOauthIdAndCreatedAt(String oauthId, LocalDateTime createdAt, LocalDateTime endDate);

}
