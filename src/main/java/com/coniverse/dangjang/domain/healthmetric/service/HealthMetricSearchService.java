package com.coniverse.dangjang.domain.healthmetric.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.healthmetric.enums.HealthMetricType;
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
	 * @param oauthId          유저 PK
	 * @param createdAt        생성일
	 * @param healthMetricType 건강 지표 타입
	 * @return HealthMetric 건강 지표
	 * @throws HealthMetricNotFoundException 유저의 건강 지표를 찾을 수 없을 경우 발생한다.
	 * @since 1.0.0
	 */
	public HealthMetric findHealthMetricById(String oauthId, LocalDate createdAt, HealthMetricType healthMetricType) {
		return healthMetricRepository.findByHealthMetricId(oauthId, createdAt, healthMetricType).orElseThrow(HealthMetricNotFoundException::new);
	}
}
