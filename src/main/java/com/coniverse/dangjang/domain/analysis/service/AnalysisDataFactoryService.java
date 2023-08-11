package com.coniverse.dangjang.domain.analysis.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.analysis.dto.healthMetric.HealthMetricAnalysisData;
import com.coniverse.dangjang.domain.analysis.factory.HealthMetricAnalysisDataFactory;
import com.coniverse.dangjang.domain.code.enums.GroupCode;
import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.user.entity.User;

/**
 * 분석 데이터를 생성하는 factory service
 *
 * @author TEO
 * @since 1.0.0
 */
@Service
public class AnalysisDataFactoryService {
	private final Map<GroupCode, HealthMetricAnalysisDataFactory> healthMetricAnalysisDataFactories;

	public AnalysisDataFactoryService(List<HealthMetricAnalysisDataFactory> healthMetricAnalysisDataFactories) {
		this.healthMetricAnalysisDataFactories = healthMetricAnalysisDataFactories.stream().collect(
			Collectors.toUnmodifiableMap(HealthMetricAnalysisDataFactory::getGroupCode, Function.identity())
		);
	}

	/**
	 * 건강지표 분석 데이터 생성
	 *
	 * @since 1.0.0
	 */
	public HealthMetricAnalysisData createHealthMetricAnalysisData(HealthMetric healthMetric, User user) {
		HealthMetricAnalysisDataFactory analysisDataFactory = healthMetricAnalysisDataFactories.get(healthMetric.getGroupCode());
		return analysisDataFactory.create(healthMetric, user);
	}
}
