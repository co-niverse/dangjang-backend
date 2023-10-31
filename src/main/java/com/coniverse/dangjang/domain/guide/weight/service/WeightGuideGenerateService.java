package com.coniverse.dangjang.domain.guide.weight.service;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.analysis.dto.AnalysisData;
import com.coniverse.dangjang.domain.analysis.dto.healthMetric.WeightAnalysisData;
import com.coniverse.dangjang.domain.code.enums.GroupCode;
import com.coniverse.dangjang.domain.guide.common.dto.response.GuideResponse;
import com.coniverse.dangjang.domain.guide.common.service.GuideGenerateService;
import com.coniverse.dangjang.domain.guide.weight.document.WeightGuide;
import com.coniverse.dangjang.domain.guide.weight.mapper.WeightMapper;
import com.coniverse.dangjang.domain.guide.weight.repository.WeightGuideRepository;

import lombok.RequiredArgsConstructor;

/**
 * 체중 가이드 생성 및 업데이트
 *
 * @author EVE
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class WeightGuideGenerateService implements GuideGenerateService {
	private final WeightGuideSearchService weightGuideSearchService;
	private final WeightGuideRepository weightGuideRepository;
	private final WeightMapper weightMapper;
	private final MongoTemplate mongoTemplate;

	/**
	 * 체중 가이드를 생성한다.
	 *
	 * @param analysisData 체중 분석 데이터
	 * @return 가이드 응답
	 * @since 1.0.0
	 */
	@Override
	public GuideResponse createGuide(AnalysisData analysisData) {
		WeightAnalysisData data = (WeightAnalysisData)analysisData;
		String content = createContent(data);
		WeightGuide weightGuide = weightMapper.toDocument(data, content);
		return weightMapper.toResponse(mongoTemplate.save(weightGuide));
	}

	/**
	 * 체중 가이드를 업데이트한다.
	 *
	 * @param analysisData 체중 분석 데이터
	 * @return 가이드 응답
	 * @since 1.0.0
	 */
	@Override
	public GuideResponse updateGuide(AnalysisData analysisData) {
		WeightAnalysisData data = (WeightAnalysisData)analysisData;
		WeightGuide weightGuide = weightGuideSearchService.findByUserIdAndCreatedAt(data.getOauthId(), data.getCreatedAt().toString());
		String content = createContent(data);
		weightGuide.changeAboutWeight(data.getWeightDiff(), data.getAlert(), content, data.getBmi(), String.valueOf(data.getUnit()));
		return weightMapper.toResponse(weightGuideRepository.save(weightGuide));
	}

	@Override
	public GroupCode getGroupCode() {
		return GroupCode.WEIGHT;
	}

	/**
	 * 체중 가이드 내용을 생성한다.
	 *
	 * @param data 체중 분석 데이터
	 * @return 가이드 내용
	 * @since 1.0.0
	 */
	public String createContent(WeightAnalysisData data) {
		return String.format("%s이에요. 평균 체중에 비해 %dkg %s", data.getAlert().getTitle(), data.getWeightDiff(),
			data.getWeightDiff() > 0 ? "많아요" : "적어요");
	}

}
