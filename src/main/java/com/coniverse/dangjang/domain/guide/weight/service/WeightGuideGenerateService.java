package com.coniverse.dangjang.domain.guide.weight.service;

import org.springframework.stereotype.Component;

import com.coniverse.dangjang.domain.analysis.dto.AnalysisData;
import com.coniverse.dangjang.domain.analysis.dto.healthMetric.WeightAnalysisData;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.code.enums.GroupCode;
import com.coniverse.dangjang.domain.guide.common.dto.GuideResponse;
import com.coniverse.dangjang.domain.guide.common.service.GuideGenerateService;
import com.coniverse.dangjang.domain.guide.weight.document.WeightGuide;
import com.coniverse.dangjang.domain.guide.weight.mapper.WeightMapper;
import com.coniverse.dangjang.domain.guide.weight.repository.WeightGuideRepository;

import lombok.RequiredArgsConstructor;

/**
 * 체중 조언서비스
 *
 * @author EVE
 * @see WeightAnalysisData
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class WeightGuideGenerateService implements GuideGenerateService {
	private final WeightGuideSearchService weightGuideSearchService;
	private final WeightGuideRepository weightGuideRepository;
	private final WeightMapper weightMapper;

	@Override
	public GuideResponse createGuide(AnalysisData analysisData) {
		WeightAnalysisData data = (WeightAnalysisData)analysisData;
		String content = String.format("%s이에요. 평균 체중에 비해 %dkg %s", data.getAlert().getTitle(), data.getWeightDiff(),
			data.getWeightDiff() > 0 ? "많아요" : "적어요");
		WeightGuide weightGuide = weightMapper.toDocument(data, content);
		return weightMapper.toResponse(weightGuideRepository.save(weightGuide));
	}

	@Override
	public GuideResponse updateGuide(AnalysisData analysisData) {
		WeightAnalysisData data = (WeightAnalysisData)analysisData;
		WeightGuide weightGuide = weightGuideSearchService.findByOauthIdAndCreatedAt(data.getOauthId(), data.getCreatedAt());
		String content = createContent(data);
		WeightGuide updatedWeightGuide = updateGuide(weightGuide, data, content);
		updatedWeightGuide.setId(weightGuide.getId());
		return weightMapper.toResponse(weightGuideRepository.save(updatedWeightGuide));
	}

	@Override
	public GuideResponse updateGuideWithType(AnalysisData analysisData, CommonCode prevType) {
		return GuideGenerateService.super.updateGuideWithType(analysisData, prevType);
	}

	@Override
	public GroupCode getGroupCode() {
		return GroupCode.WEIGHT;
	}

	public WeightGuide updateGuide(WeightGuide existGuide, WeightAnalysisData data, String content) {
		return existGuide.toBuilder()
			.todayContent(content)
			.weightDiff(data.getWeightDiff())
			.alert(data.getAlert())
			.build();
	}

	private String createContent(WeightAnalysisData data) {
		return String.format("%s이에요. 평균 체중에 비해 %dkg %s", data.getAlert().getTitle(), data.getWeightDiff(),
			data.getWeightDiff() > 0 ? "많아요" : "적어요");
	}

}
