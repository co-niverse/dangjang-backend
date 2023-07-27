package com.coniverse.dangjang.domain.healthMetric.service.bloodSugar;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.healthMetric.dto.request.HealthMetricPatchRequest;
import com.coniverse.dangjang.domain.healthMetric.dto.request.HealthMetricPostRequest;
import com.coniverse.dangjang.domain.healthMetric.dto.response.HealthMetricResponse;
import com.coniverse.dangjang.domain.healthMetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.healthMetric.enums.HealthMetricType;
import com.coniverse.dangjang.domain.healthMetric.exception.HealthMetricNotFoundException;
import com.coniverse.dangjang.domain.healthMetric.mapper.HealthMetricMapper;
import com.coniverse.dangjang.domain.healthMetric.repository.HealthMetricRepository;
import com.coniverse.dangjang.domain.healthMetric.service.HealthMetricRegistrationService;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.service.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * 혈당정보 등록 service
 *
 * @author TEO
 * @since 1.0.0
 */
@Service
@Transactional
@RequiredArgsConstructor
public class BloodSugarRegistrationService implements HealthMetricRegistrationService {
	private final HealthMetricRepository healthMetricRepository;
	private final HealthMetricMapper healthMetricMapper;
	private final UserService userService;

	@Override
	public HealthMetricResponse register(HealthMetricPostRequest request, LocalDate createdAt, String oauthId) {
		User user = userService.findUserByOauthId(oauthId);

		return healthMetricMapper.toResponse(
			healthMetricRepository.save(healthMetricMapper.toEntity(request, createdAt, user))
		);
	}

	@Override
	public HealthMetricResponse update(HealthMetricPatchRequest request, LocalDate createdAt, String oauthId) {
		HealthMetricType healthMetricType = HealthMetricType.findByTitle(request.healthMetricType());
		User user = userService.findUserByOauthId(oauthId);
		HealthMetric healthMetric = findHealthMetricById(oauthId, createdAt, healthMetricType);

		if (request.newHealthMetricType() == null) {
			healthMetric.updateUnit(request.unit());
			return healthMetricMapper.toResponse(healthMetricRepository.save(healthMetric));
		}
		healthMetricRepository.delete(healthMetric);
		return healthMetricMapper.toResponse(
			healthMetricRepository.save(healthMetricMapper.toEntity(request, createdAt, user))
		);
	}

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
	private HealthMetric findHealthMetricById(String oauthId, LocalDate createdAt, HealthMetricType healthMetricType) {
		return healthMetricRepository.findByHealthMetricId(oauthId, createdAt, healthMetricType).orElseThrow(HealthMetricNotFoundException::new);
	}
}
