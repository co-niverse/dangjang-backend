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
		@Mapping(target = "signByTTS", source = "exerciseAnalysisData.signByTTS"),
		@Mapping(target = "content", source = "guide")
	})
	ExerciseGuide toDocument(ExerciseAnalysisData exerciseAnalysisData, String guide);
}
