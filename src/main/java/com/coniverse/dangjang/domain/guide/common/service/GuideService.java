package com.coniverse.dangjang.domain.guide.common.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.analysis.dto.AnalysisData;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.code.enums.GroupCode;
import com.coniverse.dangjang.domain.guide.common.dto.response.GuideResponse;

/**
 * 가이드 service
 *
 * @author TEO
 * @since 1.0.0
 */
@Service
public class GuideService {
	private final Map<GroupCode, GuideGenerateService> guideGenerateServiceMap;

	public GuideService(List<GuideGenerateService> guideGenerateServiceMap) {
		this.guideGenerateServiceMap = guideGenerateServiceMap.stream().collect(
			Collectors.toUnmodifiableMap(GuideGenerateService::getGroupCode, Function.identity())
		);
	}

	/**
	 * 가이드를 생성한다.
	 *
	 * @since 1.0.0
	 */
	public GuideResponse createGuide(AnalysisData analysisData) {
		GuideGenerateService guideGenerateService = findGuideGenerateService(analysisData.getType());
		return guideGenerateService.createGuide(analysisData);
	}

	/**
	 * 가이드를 수정한다.
	 *
	 * @since 1.0.0
	 */
	public GuideResponse updateGuide(AnalysisData analysisData) {
		GuideGenerateService guideGenerateService = findGuideGenerateService(analysisData.getType());
		return guideGenerateService.updateGuide(analysisData);
	}

	/**
	 * 타입이 변경된 가이드를 수정한다.
	 *
	 * @since 1.0.0
	 */
	public GuideResponse updateGuideWithType(AnalysisData analysisData, CommonCode prevType) {
		GuideGenerateService guideGenerateService = findGuideGenerateService(analysisData.getType());
		return guideGenerateService.updateGuideWithType(analysisData, prevType);
	}

	/**
	 * 가이드를 삭제한다.
	 *
	 * @since 1.3.0
	 */
	public void removeGuide(String oauthId, LocalDate createdAt, CommonCode type) {
		GuideGenerateService guideGenerateService = findGuideGenerateService(type);
		guideGenerateService.removeGuide(oauthId, createdAt, type);
	}

	/**
	 * 그룹코드로 가이드 생성 서비스를 찾는다.
	 *
	 * @see GroupCode
	 * @since 1.0.0
	 */
	private GuideGenerateService findGuideGenerateService(CommonCode type) {
		GroupCode groupCode = GroupCode.findByCode(type);
		return guideGenerateServiceMap.get(groupCode);
	}
}
