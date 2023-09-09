package com.coniverse.dangjang.domain.guide.weight.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.coniverse.dangjang.domain.analysis.dto.healthMetric.WeightAnalysisData;
import com.coniverse.dangjang.domain.guide.weight.document.WeightGuide;
import com.coniverse.dangjang.domain.guide.weight.dto.WeightGuideResponse;

/**
 * 체중 가이드 Mapper
 *
 * @author EVE
 * @since 1.0.0
 */
@Mapper(componentModel = "spring")
public interface WeightMapper {

	@Mapping(target = "todayContent", source = "content")
	WeightGuide toDocument(WeightAnalysisData weightAnalysisData, String content);

	@Mapping(target = "content", source = "weightGuide.todayContent")
	WeightGuideResponse toResponse(WeightGuide weightGuide);
}
