package com.coniverse.dangjang.domain.guide.exercise.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.guide.common.exception.GuideNotFoundException;
import com.coniverse.dangjang.domain.guide.exercise.document.ExerciseGuide;
import com.coniverse.dangjang.domain.guide.exercise.dto.ExerciseGuideResponse;
import com.coniverse.dangjang.domain.guide.exercise.mapper.ExerciseGuideMapper;
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
	private final ExerciseGuideMapper exerciseGuideMapper;

	/**
	 * 운동 가이드를 전달한다.
	 *
	 * @param oauthId 유저ID
	 * @return 운동 가이드 response
	 * @Param createdAt 생성일
	 * @since 1.0.0
	 */
	public ExerciseGuideResponse findGuide(String oauthId, String createdAt) {

		return exerciseGuideMapper.toResponse(findByOauthIdAndCreatedAt(oauthId, createdAt));
	}

	/**
	 * 운동 가이드를 repository에서 조회한다.
	 *
	 * @param oauthId 유저ID
	 * @return 운동 가이드 document
	 * @return 운동 가이드 document
	 * @Param createdAt 생성일
	 * @since 1.0.0
	 */
	public ExerciseGuide findByOauthIdAndCreatedAt(String oauthId, String createdAt) {
		LocalDate localDate = LocalDate.parse(createdAt);
		localDate.atTime(9, 0, 0);
		return exerciseGuideRepository.findByOauthIdAndCreatedAt(oauthId, localDate).orElseThrow(GuideNotFoundException::new);
	}

	/**
	 * 기간 내의 운동 가이드를 조회한다
	 *
	 * @param oauthId 유저ID
	 * @return 운동 가이드 document
	 * @return 운동 가이드 document 리스트
	 * @Param createdAt 생성일
	 * @since 1.0.0
	 */

	public List<ExerciseGuide> findWeekByOauthIdAndCreatedAt(String oauthId, LocalDate startDate, LocalDate endDate) {
		return exerciseGuideRepository.findWeekByOauthIdAndCreatedAt(oauthId, startDate, endDate);
	}

}
