package com.coniverse.dangjang.domain.healthMetric.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.healthMetric.dto.request.HealthMetricPatchRequest;
import com.coniverse.dangjang.domain.healthMetric.dto.request.HealthMetricPostRequest;
import com.coniverse.dangjang.domain.healthMetric.dto.response.HealthMetricResponse;
import com.coniverse.dangjang.domain.healthMetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.healthMetric.enums.HealthMetricType;
import com.coniverse.dangjang.domain.healthMetric.mapper.HealthMetricMapper;
import com.coniverse.dangjang.domain.healthMetric.repository.HealthMetricRepository;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.service.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * 건강지표 등록 service
 *
 * @author TEO
 * @since 1.0.0
 */
@Service
@Transactional
@RequiredArgsConstructor
public class HealthMetricRegistrationService {
	private final HealthMetricRepository healthMetricRepository;
	private final HealthMetricMapper healthMetricMapper;
	private final UserService userService;
	private final HealthMetricSearchService healthMetricSearchService;

	/**
	 * 건강지표를 저장한다.
	 *
	 * @param request   건강지표 request post dto
	 * @param createdAt 건강지표 생성일
	 * @param oauthId   건강지표 등록 유저 PK
	 * @since 1.0.0
	 */
	public HealthMetricResponse register(HealthMetricPostRequest request, LocalDate createdAt, String oauthId) {
		User user = userService.findUserByOauthId(oauthId);

		return healthMetricMapper.toResponse(
			healthMetricRepository.save(healthMetricMapper.toEntity(request, createdAt, user))
		);
	}

	/**
	 * 건강지표를 수정한다.
	 *
	 * @param request   건강지표 request patch dto
	 * @param createdAt 건강지표 생성일
	 * @param oauthId   건강지표 수정 유저 PK
	 * @since 1.0.0
	 */
	public HealthMetricResponse update(HealthMetricPatchRequest request, LocalDate createdAt, String oauthId) {
		HealthMetricType healthMetricType = HealthMetricType.findByTitle(request.healthMetricType());
		User user = userService.findUserByOauthId(oauthId);
		HealthMetric healthMetric = healthMetricSearchService.findHealthMetricById(oauthId, createdAt, healthMetricType);

		if (request.newHealthMetricType() == null) {
			healthMetric.updateUnit(request.unit());
			return healthMetricMapper.toResponse(
				healthMetricRepository.save(healthMetric));
		}
		healthMetricRepository.delete(healthMetric);
		return healthMetricMapper.toResponse(
			healthMetricRepository.save(healthMetricMapper.toEntity(request, createdAt, user))
		);
	}
}
