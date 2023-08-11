package com.coniverse.dangjang.domain.analysis.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.coniverse.dangjang.domain.analysis.vo.AnalysisData.WeightAnalysisData;
import com.coniverse.dangjang.domain.feedback.document.WeightFeedback;
import com.coniverse.dangjang.global.util.EnumFindUtil;

/**
 * @author EVE
 * @since 1.0.0
 */
@Mapper(componentModel = "spring", imports = {EnumFindUtil.class})
public interface WeightMapper {
	@Mappings({
		@Mapping(target = "oauthId", source = "weightAnalysisData.oauthId"),
		@Mapping(target = "createdAt", source = "weightAnalysisData.createdAt"),
		@Mapping(target = "weightDiff", source = "weightAnalysisData.weightDiff"),
		@Mapping(target = "weightSteps", source = "weightAnalysisData.weightSteps"),
		@Mapping(target = "feedback", source = "feedback")
	})
	WeightFeedback toDocument(WeightAnalysisData weightAnalysisData, String feedback);
}
