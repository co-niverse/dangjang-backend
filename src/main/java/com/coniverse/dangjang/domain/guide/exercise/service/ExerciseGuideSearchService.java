package com.coniverse.dangjang.domain.guide.exercise.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.guide.exercise.document.ExerciseGuide;
import com.coniverse.dangjang.domain.guide.exercise.repository.ExerciseGuideRepository;

import lombok.RequiredArgsConstructor;

/**
 * 운동 가이드 조회
 *
 * @author EVE
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class ExerciseGuideSearchService {
	private final ExerciseGuideRepository exerciseGuideRepository;

	/**
	 * 운동 가이드를 조회한다.
	 *
	 * @param oauthId 유저ID
	 * @return 운동 가이드
	 * @Param createdAt 생성일
	 * @since 1.0.0
	 */
	public Optional<ExerciseGuide> findByOauthIdAndCreatedAt(String oauthId, String createdAt) {
		return exerciseGuideRepository.findByOauthIdAndCreatedAt(oauthId, createdAt);
	}

}
