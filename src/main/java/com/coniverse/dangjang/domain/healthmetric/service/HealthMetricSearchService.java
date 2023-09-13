package com.coniverse.dangjang.domain.healthmetric.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.healthmetric.exception.HealthMetricNotFoundException;
import com.coniverse.dangjang.domain.healthmetric.repository.HealthMetricRepository;

import lombok.RequiredArgsConstructor;

/**
 * 건강 지표 조회 service
 *
 * @author TEO
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HealthMetricSearchService {
	private final HealthMetricRepository healthMetricRepository;

	/**
	 * 건강 지표를 조회한다.
	 *
	 * @param oauthId   유저 PK
	 * @param createdAt 생성일
	 * @param type      건강 지표 타입
	 * @return HealthMetric 건강 지표
	 * @throws HealthMetricNotFoundException 유저의 건강 지표를 찾을 수 없을 경우 발생한다.
	 * @since 1.0.0
	 */
	public HealthMetric findByHealthMetricId(String oauthId, LocalDate createdAt, CommonCode type) {
		return healthMetricRepository.findByHealthMetricId(oauthId, createdAt, type).orElseThrow(HealthMetricNotFoundException::new);
	}

	/**
	 * 최근 건강 지표를 조회한다.
	 *
	 * @param oauthId 유저 PK
	 * @param type    건강 지표 타입
	 * @return HealthMetric 건강 지표
	 * @throws HealthMetricNotFoundException 유저의 건강 지표를 찾을 수 없을 경우 발생한다.
	 * @since 1.0.0
	 */
	public HealthMetric findLastHealthMetricById(String oauthId, CommonCode type) {
		return healthMetricRepository.findByHealthMetricId(oauthId, type).orElseThrow(HealthMetricNotFoundException::new);
	}

	/**
	 * 주간 건강 지표를 조회한다.
	 *
	 * @param oauthId 유저 PK
	 * @param type    건강 지표 타입
	 * @return HealthMetric 건강 지표
	 * @since 1.0.0
	 */
	public List<HealthMetric> findWeeklyHealthMetricById(String oauthId, CommonCode type, LocalDate startDate, LocalDate endDate) {
		return healthMetricRepository.findLastWeekByHealthMetricId(oauthId, type, startDate, endDate);
	}
}
