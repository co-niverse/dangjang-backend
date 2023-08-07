package com.coniverse.dangjang.domain.analysis.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.analysis.strategy.AnalysisStrategy;
import com.coniverse.dangjang.domain.analysis.vo.AnalysisData.AbstractAnalysisData;
import com.coniverse.dangjang.domain.code.enums.CodeGroup;

/**
 * 데이터 분석 서비스
 *
 * @author TEO
 * @since 1.0.0
 */
@Service
public class AnalysisService {
	private final Map<CodeGroup, AnalysisStrategy> analysisStrategies;

	public AnalysisService(List<AnalysisStrategy> analysisStrategies) {
		this.analysisStrategies = analysisStrategies.stream().collect(
			Collectors.toUnmodifiableMap(AnalysisStrategy::getCodeGroup, Function.identity())
		);
	}

	/**
	 * 각 데이터에 따라 분석 전략을 선택하여 호출한다.
	 *
	 * @since 1.0.0
	 */
	public void analyze(AbstractAnalysisData analysisData) {
		CodeGroup codeGroup = CodeGroup.findByCode(analysisData.getCommonCode());
		AnalysisStrategy analysisStrategy = analysisStrategies.get(codeGroup);
		analysisStrategy.analyze(analysisData);
	}
}
