package com.coniverse.dangjang.domain.analysis.strategy;

import org.springframework.stereotype.Component;

import com.coniverse.dangjang.domain.analysis.vo.AnalysisData.AbstractAnalysisData;
import com.coniverse.dangjang.domain.analysis.vo.AnalysisData.BloodSugarAnalysisData;
import com.coniverse.dangjang.domain.code.enums.CodeGroup;

/**
 * 혈당 분석 전략
 *
 * @author TEO
 * @see BloodSugarAnalysisData
 * @since 1.0.0
 */
@Component
public class BloodSugarAnalysisStrategy implements AnalysisStrategy {
	/**
	 * 혈당 데이터를 분석한다.
	 *
	 * @since 1.0.0
	 */
	@Override
	public <T extends AbstractAnalysisData> void analyze(T data) {
		BloodSugarAnalysisData analysisData = (BloodSugarAnalysisData)data;
	}

	/**
	 * 혈당 코드 그륩을 반환한다.
	 *
	 * @see CodeGroup
	 * @since 1.0.0
	 */
	@Override
	public CodeGroup getCodeGroup() {
		return CodeGroup.BS;
	}
}
