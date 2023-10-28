package com.coniverse.dangjang.domain.healthmetric.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coniverse.dangjang.domain.analysis.service.AnalysisService;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.guide.common.dto.response.GuideResponse;
import com.coniverse.dangjang.domain.guide.common.service.GuideService;
import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthMetricPatchRequest;
import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthMetricPostRequest;
import com.coniverse.dangjang.domain.healthmetric.dto.response.HealthMetricResponse;
import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.healthmetric.mapper.HealthMetricMapper;
import com.coniverse.dangjang.domain.healthmetric.repository.HealthMetricRepository;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.service.UserSearchService;
import com.coniverse.dangjang.global.util.EnumFindUtil;

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
public class HealthMetricRegisterService {
	private final HealthMetricRepository healthMetricRepository;
	private final HealthMetricMapper mapper;
	private final UserSearchService userSearchService;
	private final HealthMetricSearchService healthMetricSearchService;
	private final AnalysisService analysisService;
	private final GuideService guideService;

	/**
	 * 건강지표를 저장한다.
	 *
	 * @param request 건강지표 request post dto
	 * @param oauthId 건강지표 등록 유저 PK
	 * @since 1.0.0
	 */
	public HealthMetricResponse register(HealthMetricPostRequest request, String oauthId) {
		final User user = userSearchService.findUserByOauthId(oauthId);
		final HealthMetric healthMetric = healthMetricRepository.save(mapper.toEntity(request, user));
		final GuideResponse guideResponse = guideService.createGuide(analysisService.analyze(healthMetric));
		return mapper.toResponse(healthMetric, guideResponse);
	}

	/**
	 * 건강지표를 수정한다.
	 *
	 * @param request 건강지표 request patch dto
	 * @param oauthId 건강지표 수정 유저 PK
	 * @since 1.0.0
	 */
	public HealthMetricResponse update(HealthMetricPatchRequest request, String oauthId) {
		final CommonCode type = EnumFindUtil.findByTitle(CommonCode.class, request.type());
		final LocalDate createdAt = LocalDate.parse(request.createdAt());
		HealthMetric healthMetric = healthMetricSearchService.findByHealthMetricId(oauthId, createdAt, type);
		if (request.isBlankNewType()) {
			return this.updateUnit(healthMetric, request.unit());
		}
		return this.updateType(healthMetric, request, oauthId);
	}

	/**
	 * 단위를 수정한다.
	 *
	 * @param healthMetric 건강지표
	 * @param unit         단위
	 * @since 1.0.0
	 */
	private HealthMetricResponse updateUnit(HealthMetric healthMetric, String unit) {
		healthMetric.updateUnit(unit);
		HealthMetric newHealthMetric = healthMetricRepository.save(healthMetric);
		final GuideResponse guideResponse = guideService.updateGuide(analysisService.analyze(healthMetric));
		return mapper.toResponse(newHealthMetric, guideResponse);
	}

	/**
	 * type을 수정한다.
	 * <p>
	 * type은 PK이기 때문에 삭제 후 새로운 type으로 저장한다.
	 *
	 * @param prevHealthMetric 이전 건강지표
	 * @param request          건강지표 request patch dto
	 * @param oauthId          건강지표 수정 유저 PK
	 * @since 1.0.0
	 */
	private HealthMetricResponse updateType(HealthMetric prevHealthMetric, HealthMetricPatchRequest request, String oauthId) {
		final User user = userSearchService.findUserByOauthId(oauthId);
		prevHealthMetric.verifySameGroupCode(EnumFindUtil.findByTitle(CommonCode.class, request.newType()));
		healthMetricRepository.delete(prevHealthMetric);
		final HealthMetric healthMetric = healthMetricRepository.save(mapper.toEntity(request, user));
		final GuideResponse guideResponse = guideService.updateGuideWithType(analysisService.analyze(healthMetric), prevHealthMetric.getType());
		return mapper.toResponse(healthMetric, guideResponse);
	}

	/**
	 * 건강지표를 삭제한다.
	 *
	 * @param date        건강지표 생성일
	 * @param requestType 건강지표 타입
	 * @param oauthId     건강지표 삭제 유저 PK
	 * @since 1.3.0
	 */
	public void remove(String date, String requestType, String oauthId) {
		CommonCode type = EnumFindUtil.findByTitle(CommonCode.class, requestType);
		LocalDate createdAt = LocalDate.parse(date);
		HealthMetric healthMetric = healthMetricSearchService.findByHealthMetricId(oauthId, createdAt, type);
		healthMetricRepository.delete(healthMetric);
	}
}
