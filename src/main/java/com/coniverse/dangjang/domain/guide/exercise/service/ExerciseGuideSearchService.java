package com.coniverse.dangjang.domain.guide.exercise.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.guide.exercise.document.ExerciseGuide;
import com.coniverse.dangjang.domain.guide.exercise.repository.ExerciseGuideRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExerciseGuideSearchService {
	private final ExerciseGuideRepository exerciseGuideRepository;

	public Optional<ExerciseGuide> findByOauthIdAndCreatedAt(String oauthId, LocalDate createdAt) {
		return exerciseGuideRepository.findByOauthIdAndCreatedAt(oauthId, createdAt);
	}

}
