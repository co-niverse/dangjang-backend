package com.coniverse.dangjang.domain.healthmetric.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.analysis.service.AnalysisService;
import com.coniverse.dangjang.domain.analysis.vo.analysisdata.HealthMetricAnalysisData;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.feedback.entity.BloodSugarFeedback;
import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthMetricPatchRequest;
import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthMetricPostRequest;
import com.coniverse.dangjang.domain.healthmetric.dto.response.HealthMetricResponse;
import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.healthmetric.mapper.HealthMetricMapper;
import com.coniverse.dangjang.domain.healthmetric.repository.HealthMetricRepository;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.service.UserSearchService;
import com.coniverse.dangjang.global.util.EnumFindUtil;

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
	private final UserSearchService userSearchService;
	private final HealthMetricSearchService healthMetricSearchService;
	private final AnalysisService<HealthMetricAnalysisData, BloodSugarFeedback> analysisService;

	/**
	 * 건강지표를 저장한다.
	 *
	 * @param request 건강지표 request post dto
	 * @param oauthId 건강지표 등록 유저 PK
	 * @since 1.0.0
	 */
	public HealthMetricResponse register(HealthMetricPostRequest request, String oauthId) {
		User user = userSearchService.findUserByOauthId(oauthId);
		HealthMetric healthMetric = healthMetricRepository.save(healthMetricMapper.toEntity(request, user));
		BloodSugarFeedback feedback = generateFeedback(healthMetric, user);
		return healthMetricMapper.toResponse(healthMetric);
	}

	/**
	 * 건강지표를 수정한다.
	 *
	 * @param request 건강지표 request patch dto
	 * @param oauthId 건강지표 수정 유저 PK
	 * @since 1.0.0
	 */
	public HealthMetricResponse update(HealthMetricPatchRequest request, String oauthId) {
		CommonCode type = EnumFindUtil.findByTitle(CommonCode.class, request.type());
		LocalDate createdAt = LocalDate.parse(request.createdAt());
		HealthMetric healthMetric = healthMetricSearchService.findByHealthMetricId(oauthId, createdAt, type);
		User user = userSearchService.findUserByOauthId(oauthId);

		HealthMetric updatedHealthMetric;
		if (request.isEmptyNewType()) {
			updatedHealthMetric = updateUnit(healthMetric, request.unit());
		} else {
			updatedHealthMetric = updateType(healthMetric, request, user);
		}
		BloodSugarFeedback feedback = generateFeedback(updatedHealthMetric, user);
		return healthMetricMapper.toResponse(updatedHealthMetric);
	}

	/**
	 * 조언을 생성한다.
	 *
	 * @param healthMetric 건강지표
	 * @param user         건강지표 등록 or 수정 유저
	 * @since 1.0.0
	 */
	private BloodSugarFeedback generateFeedback(HealthMetric healthMetric, User user) {
		HealthMetricAnalysisData analysisData = HealthMetricAnalysisData.of(healthMetric, user);
		return analysisService.analyze(analysisData, healthMetric.getGroupCode());
	}

	/**
	 * unit을 수정한다.
	 *
	 * @param healthMetric 건강지표
	 * @param unit         unit
	 * @since 1.0.0
	 */
	private HealthMetric updateUnit(HealthMetric healthMetric, String unit) {
		healthMetric.updateUnit(unit);
		return healthMetricRepository.save(healthMetric);
	}

	/**
	 * type을 수정한다.
	 * <p>
	 * type은 PK이기 때문에 삭제 후 새로운 type으로 저장한다.
	 *
	 * @param healthMetric 건강지표
	 * @param request      건강지표 request patch dto
	 * @param user         건강지표 수정 유저
	 * @since 1.0.0
	 */
	private HealthMetric updateType(HealthMetric healthMetric, HealthMetricPatchRequest request, User user) {
		healthMetric.verifySameGroupCode(EnumFindUtil.findByTitle(CommonCode.class, request.newType()));
		healthMetricRepository.delete(healthMetric);
		return healthMetricRepository.save(healthMetricMapper.toEntity(request, user));
	}
}
