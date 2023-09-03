package com.coniverse.dangjang.domain.healthmetric.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.analysis.dto.healthMetric.HealthMetricAnalysisData;
import com.coniverse.dangjang.domain.analysis.service.AnalysisDataFactoryService;
import com.coniverse.dangjang.domain.analysis.service.AnalysisService;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.guide.common.dto.GuideResponse;
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
public class HealthMetricRegisterService { // TODO 이름 변경
	private final HealthMetricRepository healthMetricRepository;
	private final HealthMetricMapper mapper;
	private final UserSearchService userSearchService;
	private final HealthMetricSearchService healthMetricSearchService;
	private final AnalysisService analysisService;
	private final AnalysisDataFactoryService analysisDataFactoryService;

	/**
	 * 건강지표를 저장한다.
	 *
	 * @param request 건강지표 request post dto
	 * @param oauthId 건강지표 등록 유저 PK
	 * @since 1.0.0
	 */
	public HealthMetricResponse register(HealthMetricPostRequest request, String oauthId) {
		final User user = userSearchService.findUserByOauthId(oauthId);
		final HealthMetric healthMetric = mapper.toEntity(request, user);
		final GuideResponse guideResponse = this.requestAnalysis(healthMetric);
		healthMetric.updateGuideId(guideResponse.id());
		return mapper.toResponse(healthMetricRepository.save(healthMetric), guideResponse);
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

		if (request.isEmptyNewType()) {
			healthMetric.updateUnit(request.unit());
		} else {
			User user = userSearchService.findUserByOauthId(oauthId);
			healthMetric = this.updateType(healthMetric, request, user);
		}
		final GuideResponse guideResponse = this.requestAnalysis(healthMetric);
		return mapper.toResponse(healthMetricRepository.save(healthMetric), guideResponse);
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
		final HealthMetric updatedHealthMetric = mapper.toEntity(request, user);
		updatedHealthMetric.updateGuideId(healthMetric.getGuideId());
		return updatedHealthMetric;
	}

	/**
	 * 분석을 요청한다.
	 *
	 * @param healthMetric 건강지표
	 * @return 가이드 응답
	 * @since 1.0.0
	 */
	private GuideResponse requestAnalysis(HealthMetric healthMetric) {
		HealthMetricAnalysisData analysisData = analysisDataFactoryService.createHealthMetricAnalysisData(healthMetric);
		return analysisService.analyze(analysisData);
	}
}
