package com.coniverse.dangjang.domain.analysis.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.analysis.dto.AnalysisData;
import com.coniverse.dangjang.domain.analysis.strategy.AnalysisStrategy;
import com.coniverse.dangjang.domain.code.enums.GroupCode;
import com.coniverse.dangjang.domain.guide.common.dto.GuideResponse;

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
	 * 각 데이터에 따라 분석 전략을 선택하여 호출한다.
	 *
	 * @param data 분석할 데이터
	 * @return 가이드 응답
	 * @since 1.0.0
	 */
	public GuideResponse analyze(AnalysisData data) {
		AnalysisStrategy analysisStrategy = analysisStrategies.get(data.getGroupCode());
		return analysisStrategy.analyze(data);
	}
}
