package com.coniverse.dangjang.domain.analysis.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.coniverse.dangjang.domain.analysis.vo.AnalysisData.ExerciseAnalysisData;
import com.coniverse.dangjang.domain.feedback.document.ExerciseGuide;
import com.coniverse.dangjang.global.util.EnumFindUtil;

@Mapper(componentModel = "spring", imports = {EnumFindUtil.class})
public interface ExerciseMapper {
	@Mappings({
		@Mapping(target = "oauthId", source = "exerciseAnalysisData.oauthId"),
		@Mapping(target = "createdAt", source = "exerciseAnalysisData.createdAt"),
		@Mapping(target = "needStepByTTS", source = "exerciseAnalysisData.needStepByTTS"),
		@Mapping(target = "needStepByLastWeek", source = "exerciseAnalysisData.needStepByLastWeek"),
		@Mapping(target = "signByTTS", source = "exerciseAnalysisData.signByTTS"),
		@Mapping(target = "content", source = "guide"),
		@Mapping(target = "comparedToLastWeek", source = "comparedToLastWeek")
		// @Mapping(target = "walkCalorie", ignore = true),
		// @Mapping(target = "hikeCalorie", ignore = true),
		// @Mapping(target = "runCalorie", ignore = true),
		// @Mapping(target = "bikeCalorie", ignore = true),
		// @Mapping(target = "swimCalorie", ignore = true),
		// @Mapping(target = "healthCalorie", ignore = true)
	})
	ExerciseGuide toDocument(ExerciseAnalysisData exerciseAnalysisData, String guide, String comparedToLastWeek);
}
