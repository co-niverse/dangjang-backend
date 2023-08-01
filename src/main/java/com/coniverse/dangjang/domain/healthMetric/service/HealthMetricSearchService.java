package com.coniverse.dangjang.domain.healthMetric.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coniverse.dangjang.domain.healthMetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.healthMetric.enums.HealthMetricType;
import com.coniverse.dangjang.domain.healthMetric.exception.HealthMetricNotFoundException;
import com.coniverse.dangjang.domain.healthMetric.repository.HealthMetricRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HealthMetricSearchService {
	private final HealthMetricRepository healthMetricRepository;

	/**
	 * 혈당을 조회한다.
	 *
	 * @param oauthId          유저 PK
	 * @param createdAt        생성일
	 * @param healthMetricType 혈당 타입
	 * @return HealthMetric 혈당
	 * @throws HealthMetricNotFoundException 유저의 혈당 정보를 찾을 수 없을 경우 발생한다.
	 * @since 1.0.0
	 */
	public HealthMetric findHealthMetricById(String oauthId, LocalDate createdAt, HealthMetricType healthMetricType) {
		return healthMetricRepository.findByHealthMetricId(oauthId, createdAt, healthMetricType).orElseThrow(HealthMetricNotFoundException::new);
	}
}
