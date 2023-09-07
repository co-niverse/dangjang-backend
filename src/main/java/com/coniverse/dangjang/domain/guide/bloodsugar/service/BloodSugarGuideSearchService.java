package com.coniverse.dangjang.domain.guide.bloodsugar.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.guide.bloodsugar.document.BloodSugarGuide;
import com.coniverse.dangjang.domain.guide.bloodsugar.repository.BloodSugarGuideRepository;
import com.coniverse.dangjang.domain.guide.common.exception.GuideNotFoundException;

import lombok.RequiredArgsConstructor;

/**
 * 혈당 가이드 조회 service
 *
 * @author TEO
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class BloodSugarGuideSearchService {
	private final BloodSugarGuideRepository bloodSugarGuideRepository;

	/**
	 * 사용자 PK와 생성일자로 혈당 가이드를 조회한다.
	 *
	 * @param oauthId   사용자 PK
	 * @param createdAt 생성일자
	 * @return 혈당 가이드
	 * @throws GuideNotFoundException 혈당 가이드를 찾을 수 없을 경우 발생한다.
	 * @since 1.0.0
	 */
	public BloodSugarGuide findByUserIdAndCreatedAt(String oauthId, LocalDate createdAt) {
		return bloodSugarGuideRepository.findByOauthIdAndCreatedAt(oauthId, createdAt).orElseThrow(GuideNotFoundException::new);
	}
}
