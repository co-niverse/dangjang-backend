package com.coniverse.dangjang.domain.analysis.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.analysis.dto.AnalysisData;
import com.coniverse.dangjang.domain.analysis.strategy.AnalysisStrategy;
import com.coniverse.dangjang.domain.code.enums.GroupCode;
import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;

/**
 * 데이터 분석 서비스
 *
 * @author TEO
 * @since 1.0.0
 */
@Service
public class AnalysisService {
	private final Map<GroupCode, AnalysisStrategy> analysisStrategies;

	public AnalysisService(List<AnalysisStrategy> analysisStrategies) {
		this.analysisStrategies = analysisStrategies.stream().collect(
			Collectors.toUnmodifiableMap(AnalysisStrategy::getGroupCode, Function.identity())
		);
	}

	/**
	 * 각 건강지표에 따라 분석 전략을 선택하여 호출한다.
	 *
	 * @param healthMetric 분석할 건강지표
	 * @return 분석 결과
	 * @since 1.0.0
	 */
	public AnalysisData analyze(HealthMetric healthMetric) {
		AnalysisStrategy analysisStrategy = analysisStrategies.get(healthMetric.getGroupCode());
		AnalysisData analysisData = analysisStrategy.createAnalysisData(healthMetric);
		return analysisStrategy.analyze(analysisData);
	}
}
