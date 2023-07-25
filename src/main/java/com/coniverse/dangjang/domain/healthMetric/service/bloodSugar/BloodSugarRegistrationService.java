package com.coniverse.dangjang.domain.healthMetric.service.bloodSugar;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.healthMetric.dto.request.HealthMetricPatchRequest;
import com.coniverse.dangjang.domain.healthMetric.dto.request.HealthMetricPostRequest;
import com.coniverse.dangjang.domain.healthMetric.dto.response.HealthMetricResponse;
import com.coniverse.dangjang.domain.healthMetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.healthMetric.entity.HealthMetricId;
import com.coniverse.dangjang.domain.healthMetric.enums.HealthMetricCode;
import com.coniverse.dangjang.domain.healthMetric.enums.HealthMetricType;
import com.coniverse.dangjang.domain.healthMetric.exception.HealthMetricAlreadyExistentException;
import com.coniverse.dangjang.domain.healthMetric.exception.HealthMetricNotFoundException;
import com.coniverse.dangjang.domain.healthMetric.exception.IncorrectTypeUpdateException;
import com.coniverse.dangjang.domain.healthMetric.mapper.BloodSugarMapper;
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
	private final BloodSugarMapper bloodSugarMapper;
	private final UserService userService;

	// TODO user
	@Override
	public HealthMetricResponse register(HealthMetricPostRequest request, LocalDate createdAt) {
		HealthMetricType healthMetricType = HealthMetricType.findByTitle(request.healthMetricType());
		HealthMetricCode healthMetricCode = HealthMetricCode.findByHealthMetricType(healthMetricType);
		User user = User.builder().oauthId("1234kakao").build();
		HealthMetric healthMetric = generateHealthMetric(createdAt, healthMetricCode, healthMetricType, request.unit(), user);

		return bloodSugarMapper.toResponse(healthMetricRepository.save(healthMetric));
	}

	// TODO user
	@Override
	public HealthMetricResponse update(HealthMetricPatchRequest request, LocalDate createdAt) {
		HealthMetricType prevHealthMetricType = HealthMetricType.findByTitle(request.prevHealthMetricType());
		HealthMetricType curHealthMetricType = HealthMetricType.findByTitle(request.curHealthMetricType());
		User user = User.builder().oauthId("1234kakao").build();
		if (prevHealthMetricType == curHealthMetricType) {
			HealthMetric healthMetric = findPreviousHealthMetric(createdAt, HealthMetricCode.findByHealthMetricType(prevHealthMetricType), prevHealthMetricType,
				user.getOauthId());
			healthMetric.updateUnit(request.unit());
			return bloodSugarMapper.toResponse(healthMetricRepository.save(healthMetric));
		}
		HealthMetricCode healthMetricCode = verifySameHealthMetricCodeAndGet(prevHealthMetricType, curHealthMetricType);
		HealthMetric prevHealthMetric = findPreviousHealthMetric(createdAt, healthMetricCode, prevHealthMetricType, user.getOauthId());
		healthMetricRepository.delete(prevHealthMetric);
		HealthMetric healthMetric = saveCurrentHealthMetric(createdAt, healthMetricCode, curHealthMetricType, request.unit(), user);

		return bloodSugarMapper.toResponse(healthMetricRepository.save(healthMetric));
	}

	/**
	 * 건강지표를 생성한다.
	 *
	 * @since 1.0.0
	 */
	private HealthMetric generateHealthMetric(LocalDate createdAt, HealthMetricCode healthMetricCode, HealthMetricType healthMetricType, String unit,
		User user) {
		return HealthMetric.builder()
			.createdAt(createdAt)
			.healthMetricCode(healthMetricCode)
			.healthMetricType(healthMetricType)
			.unit(unit)
			.user(user)
			.build();
	}

	/**
	 * 이전 건강지표 타입과 현재 건강지표 타입이 같은 건강지표 코드인지 검증한다.
	 *
	 * @param prevHealthMetricType 이전 건강지표 타입
	 * @param curHealthMetricType  현재 건강지표 타입
	 * @return HealthMetricCode 건강지표 코드
	 * @throws IncorrectTypeUpdateException 이전 건강지표 타입과 현재 건강지표 타입이 다른 건강지표 코드일 경우 발생한다.
	 * @since 1.0.0
	 */
	private HealthMetricCode verifySameHealthMetricCodeAndGet(HealthMetricType prevHealthMetricType, HealthMetricType curHealthMetricType) {
		HealthMetricCode prevHealthMetricCode = HealthMetricCode.findByHealthMetricType(prevHealthMetricType);
		HealthMetricCode curHealthMetricCode = HealthMetricCode.findByHealthMetricType(curHealthMetricType);
		if (prevHealthMetricCode != curHealthMetricCode) {
			throw new IncorrectTypeUpdateException();
		}
		return prevHealthMetricCode;
	}

	/**
	 * 이전 건강지표를 삭제한다.
	 *
	 * @param createdAt        생성일
	 * @param healthMetricCode 건강지표 코드
	 * @param healthMetricType 건강지표 타입
	 * @param oauthId          oauthId
	 * @return HealthMetric 이전 건강지표
	 * @throws HealthMetricNotFoundException 이전 건강지표가 존재하지 않을 경우 발생한다.
	 * @since 1.0.0
	 */
	private HealthMetric findPreviousHealthMetric(LocalDate createdAt, HealthMetricCode healthMetricCode, HealthMetricType healthMetricType, String oauthId) {
		HealthMetricId id = HealthMetricId.builder()
			.createdAt(createdAt)
			.healthMetricCode(healthMetricCode)
			.healthMetricType(healthMetricType)
			.oauthId(oauthId)
			.build();
		return healthMetricRepository.findByHealthMetricId(id).orElseThrow(HealthMetricNotFoundException::new);
	}

	/**
	 * 현재 건강지표를 저장한다.
	 *
	 * @since 1.0.0
	 */
	private HealthMetric saveCurrentHealthMetric(LocalDate createdAt, HealthMetricCode healthMetricCode, HealthMetricType healthMetricType, String unit,
		User user) {
		HealthMetricId id = HealthMetricId.builder()
			.createdAt(createdAt)
			.healthMetricCode(healthMetricCode)
			.healthMetricType(healthMetricType)
			.oauthId(user.getOauthId())
			.build();

		if (healthMetricRepository.findByHealthMetricId(id).isPresent()) {
			throw new HealthMetricAlreadyExistentException();
		}
		HealthMetric healthMetric = generateHealthMetric(createdAt, healthMetricCode, healthMetricType, unit, user);
		return healthMetricRepository.save(healthMetric);
	}
}
