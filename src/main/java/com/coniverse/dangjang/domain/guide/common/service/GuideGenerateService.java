package com.coniverse.dangjang.domain.guide.common.service;

import com.coniverse.dangjang.domain.analysis.dto.AnalysisData;
import com.coniverse.dangjang.domain.guide.common.dto.GuideResponse;

/**
 * 가이드 생성 service
 *
 * @author TEO
 * @since 1.0.0
 */
public interface GuideGenerateService {
	<T extends AnalysisData> GuideResponse generateGuide(T analysisData);
}
