package com.coniverse.dangjang.domain.guide.weight.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.coniverse.dangjang.domain.analysis.dto.healthMetric.WeightAnalysisData;
import com.coniverse.dangjang.domain.guide.weight.document.WeightGuide;

/**
 * 체중 가이드 Mapper
 *
 * @author EVE
 * @since 1.0.0
 */
@Mapper(componentModel = "spring")
public interface WeightMapper {
	@Mapping(target = "title", source = "data.alert.title")
	WeightGuide toDocument(WeightAnalysisData data, String content);
}
