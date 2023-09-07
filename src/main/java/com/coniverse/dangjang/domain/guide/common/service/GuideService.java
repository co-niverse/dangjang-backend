package com.coniverse.dangjang.domain.guide.common.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.analysis.dto.AnalysisData;
import com.coniverse.dangjang.domain.code.enums.GroupCode;
import com.coniverse.dangjang.domain.guide.common.dto.GuideResponse;

@Service
public class GuideService {
	private final Map<GroupCode, GuideGenerateService> guideGenerateServiceMap;

	public GuideService(List<GuideGenerateService> guideGenerateServiceMap) {
		this.guideGenerateServiceMap = guideGenerateServiceMap.stream().collect(
			Collectors.toUnmodifiableMap(GuideGenerateService::getGroupCode, Function.identity())
		);
	}

	public GuideResponse createGuide(AnalysisData analysisData) {
		GroupCode groupCode = GroupCode.findByCode(analysisData.getType());
		return guideGenerateServiceMap.get(groupCode).generateGuide(analysisData);
	}
}
