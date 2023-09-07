package com.coniverse.dangjang.domain.healthmetric.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.analysis.service.AnalysisService;
import com.coniverse.dangjang.domain.analysis.vo.AnalysisData.BloodSugarAnalysisData;
import com.coniverse.dangjang.domain.analysis.vo.AnalysisData.ExerciseAnalysisData;
import com.coniverse.dangjang.domain.analysis.vo.AnalysisData.WeightAnalysisData;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
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
	private final AnalysisService analysisService;

	/**
	 * 건강지표를 저장한다.
	 *
	 * @param request   건강지표 request post dto
	 * @param createdAt 건강지표 생성일
	 * @param oauthId   건강지표 등록 유저 PK
	 * @since 1.0.0
	 */
	public HealthMetricResponse register(HealthMetricPostRequest request, LocalDate createdAt, String oauthId) {
		User user = userSearchService.findUserByOauthId(oauthId);
		HealthMetric healthMetric = healthMetricRepository.save(healthMetricMapper.toEntity(request, createdAt, user));
		// TODO: 분석 서비스 분리
		CommonCode commonCode = healthMetric.getCommonCode();
		if (commonCode.equals((CommonCode.WT_MEM))) {
			analysisService.analyze(new WeightAnalysisData(healthMetric, user));
		} else if (commonCode.equals(CommonCode.EC_STC) || commonCode.equals(CommonCode.EC_WLK) || commonCode.equals(CommonCode.EC_RN) || commonCode.equals(
			CommonCode.EC_HIK) || commonCode.equals(CommonCode.EC_SW) || commonCode.equals(CommonCode.EC_HT)) {
			analysisService.analyze(new ExerciseAnalysisData(healthMetric, user, healthMetricSearchService));
		} else {
			analysisService.analyze(new BloodSugarAnalysisData(healthMetric, user));
		}

		return healthMetricMapper.toResponse(healthMetric);
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
		CommonCode commonCode = EnumFindUtil.findByTitle(CommonCode.class, request.title());
		User user = userSearchService.findUserByOauthId(oauthId);
		HealthMetric healthMetric = healthMetricSearchService.findHealthMetricById(oauthId, createdAt, commonCode);

		if (request.newTitle() == null) {
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
