package com.coniverse.dangjang.domain.guide.common.service;

import com.coniverse.dangjang.domain.analysis.dto.AnalysisData;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.code.enums.GroupCode;
import com.coniverse.dangjang.domain.guide.common.dto.GuideResponse;

/**
 * 가이드 생성 service
 *
 * @author TEO
 * @since 1.0.0
 */
public interface GuideGenerateService {
	GuideResponse createGuide(AnalysisData analysisData);

	GuideResponse updateGuide(AnalysisData analysisData);

	default GuideResponse updateGuideWithType(AnalysisData analysisData, CommonCode prevType) {
		throw new UnsupportedOperationException("잘못된 메서드 호출입니다.");
	}

	GroupCode getGroupCode();
}
