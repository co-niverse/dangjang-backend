package com.coniverse.dangjang.domain.analysis.dto;

import com.coniverse.dangjang.domain.code.enums.GroupCode;

/**
 * 분석 데이터 인터페이스
 *
 * @author TEO
 * @since 1.0.0
 */
public interface AnalysisData { // TODO strategy pattern
	GroupCode getGroupCode();
}
