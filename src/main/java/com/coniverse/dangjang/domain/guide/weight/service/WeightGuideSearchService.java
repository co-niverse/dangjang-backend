package com.coniverse.dangjang.domain.guide.weight.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.guide.common.exception.GuideNotFoundException;
import com.coniverse.dangjang.domain.guide.weight.document.WeightGuide;
import com.coniverse.dangjang.domain.guide.weight.dto.WeightGuideResponse;
import com.coniverse.dangjang.domain.guide.weight.mapper.WeightMapper;
import com.coniverse.dangjang.domain.guide.weight.repository.WeightGuideRepository;

import lombok.RequiredArgsConstructor;

/**
 * 체중 가이드 조회
 *
 * @author EVE
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class WeightGuideSearchService {
	private final WeightGuideRepository weightGuideRepository;
	private final WeightMapper weightMapper;

	/**
	 * 날짜별 체중 가이드를 조회한다.
	 *
	 * @param oauthId   유저ID
	 * @param createdAt 생성일
	 * @return 운동 가이드 반응
	 * @since 1.0.0
	 */
	public WeightGuideResponse findGuide(String oauthId, LocalDate createdAt) {
		return weightMapper.toResponse(findByUserIdAndCreatedAt(oauthId, createdAt));
	}

	/**
	 * 체중 가이드를 조회한다.
	 *
	 * @param oauthId 유저ID
	 * @return 운동 가이드
	 * @Param createdAt 생성일
	 * @since 1.0.0
	 */
	public WeightGuide findByUserIdAndCreatedAt(String oauthId, LocalDate createdAt) {
		return weightGuideRepository.findByOauthIdAndCreatedAt(oauthId, createdAt).orElseThrow(GuideNotFoundException::new);
	}

}
