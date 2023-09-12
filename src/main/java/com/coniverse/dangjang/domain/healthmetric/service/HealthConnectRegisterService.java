package com.coniverse.dangjang.domain.healthmetric.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coniverse.dangjang.domain.analysis.service.AnalysisService;
import com.coniverse.dangjang.domain.guide.common.service.GuideService;
import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthConnectPostRequest;
import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.healthmetric.mapper.HealthMetricMapper;
import com.coniverse.dangjang.domain.healthmetric.repository.HealthMetricRepository;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.service.UserSearchService;

import lombok.RequiredArgsConstructor;

/**
 * health connect 건강지표 등록 service
 *
 * @author TEO
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class HealthConnectRegisterService {
	private final UserSearchService userSearchService;
	private final HealthMetricMapper healthMetricMapper;
	private final HealthMetricRepository healthMetricRepository;
	private final GuideService guideService;
	private final AnalysisService analysisService;

	/**
	 * health connect로 받은 n개의 건강 지표 데이터를 등록한다.
	 *
	 * @param request 건강 지표 request post dto n개
	 * @param oauthId 건강 지표 등록 유저 PK
	 * @since 1.0.0
	 */
	public void registerHealthConnect(HealthConnectPostRequest request, String oauthId) { // TODO async
		final User user = userSearchService.findUserByOauthId(oauthId);

		List<HealthMetric> healthMetrics = request.data()
			.stream()
			.map(postRequest -> healthMetricMapper.toEntity(postRequest, user))
			.filter(h -> healthMetricRepository
				.findByHealthMetricId(h.getOauthId(), h.getCreatedAt(), h.getType())
				.isEmpty()
			)
			.toList();
		healthMetricRepository.saveAll(healthMetrics);
		healthMetrics.forEach(h -> guideService.createGuide(analysisService.analyze(h)));
	}
}
