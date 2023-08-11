package com.coniverse.dangjang.domain.analysis.strategy;

import com.coniverse.dangjang.domain.code.enums.GroupCode;

/**
 * 분석 전략 interface
 *
 * @param <T> 분석할 데이터
 * @param <R> 분석 결과
 * @author TEO
 * @since 1.0.0
 */
public interface AnalysisStrategy<T, R> {
	R analyze(T data);

	GroupCode getCodeGroup();
}
