package com.coniverse.dangjang.domain.analysis.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.analysis.strategy.AnalysisStrategy;
import com.coniverse.dangjang.domain.code.enums.GroupCode;

/**
 * 데이터 분석 서비스
 *
 * @param <T> 분석할 데이터
 * @param <R> 분석 결과
 * @author TEO
 * @since 1.0.0
 */
@Service
public class AnalysisService<T, R> {
	private final Map<GroupCode, AnalysisStrategy<T, R>> analysisStrategies;

	public AnalysisService(List<AnalysisStrategy<T, R>> analysisStrategies) {
		this.analysisStrategies = analysisStrategies.stream().collect(
			Collectors.toUnmodifiableMap(AnalysisStrategy::getCodeGroup, Function.identity())
		);
	}

	/**
	 * 각 데이터에 따라 분석 전략을 선택하여 호출한다.
	 *
	 * @since 1.0.0
	 */
	public R analyze(T data, GroupCode groupCode) {
		AnalysisStrategy<T, R> analysisStrategy = analysisStrategies.get(groupCode);
		return analysisStrategy.analyze(data);
	}
}
