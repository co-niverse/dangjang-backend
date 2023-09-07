package com.coniverse.dangjang.domain.guide.weight.mapper;

import org.mapstruct.Mapper;

import com.coniverse.dangjang.domain.analysis.dto.healthMetric.WeightAnalysisData;
import com.coniverse.dangjang.domain.guide.weight.document.WeightGuide;
import com.coniverse.dangjang.global.util.EnumFindUtil;

/**
 * 체중 가이드 Mapper
 *
 * @author EVE
 * @since 1.0.0
 */
@Mapper(componentModel = "spring", imports = {EnumFindUtil.class})
public interface WeightMapper {
	// @Mappings({
	// 	@Mapping(target = "oauthId", source = "weightAnalysisData.oauthId"),
	// 	@Mapping(target = "createdAt", source = "weightAnalysisData.createdAt"),
	// 	@Mapping(target = "weightDiff", source = "weightAnalysisData.weightDiff"),
	// 	@Mapping(target = "weightStep", source = "weightAnalysisData.weightStep"),
	// 	@Mapping(target = "content", source = "content")
	// })
	WeightGuide toDocument(WeightAnalysisData weightAnalysisData, String content);
}
