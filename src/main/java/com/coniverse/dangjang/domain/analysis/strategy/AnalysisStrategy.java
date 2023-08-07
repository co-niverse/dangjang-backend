package com.coniverse.dangjang.domain.analysis.strategy;

import com.coniverse.dangjang.domain.analysis.vo.AnalysisData.AbstractAnalysisData;
import com.coniverse.dangjang.domain.code.enums.CodeGroup;

/**
 * 분석 전략 interface
 *
 * @author TEO
 * @see AbstractAnalysisData
 * @since 1.0.0
 */
public interface AnalysisStrategy {
	<T extends AbstractAnalysisData> void analyze(T data); // TODO 조언 return으로 수정

	CodeGroup getCodeGroup();
}
