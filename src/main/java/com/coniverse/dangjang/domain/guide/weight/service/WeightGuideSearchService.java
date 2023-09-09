package com.coniverse.dangjang.domain.guide.weight.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.guide.weight.document.WeightGuide;
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

	/**
	 * 체중 가이드를 조회한다.
	 *
	 * @param oauthId 유저ID
	 * @return 운동 가이드
	 * @Param createdAt 생성일
	 * @since 1.0.0
	 */
	public WeightGuide findByOauthIdAndCreatedAt(String oauthId, LocalDate createdAt) {
		return weightGuideRepository.findByOauthIdAndCreatedAt(oauthId, createdAt);
	}
}
