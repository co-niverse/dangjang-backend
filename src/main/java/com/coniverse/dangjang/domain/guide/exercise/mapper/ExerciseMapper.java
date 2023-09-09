package com.coniverse.dangjang.domain.guide.exercise.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.coniverse.dangjang.domain.analysis.dto.healthMetric.ExerciseAnalysisData;
import com.coniverse.dangjang.domain.guide.exercise.document.ExerciseGuide;
import com.coniverse.dangjang.domain.guide.exercise.dto.ExerciseCalorie;
import com.coniverse.dangjang.domain.guide.exercise.dto.ExerciseGuideResponse;
import com.coniverse.dangjang.global.util.EnumFindUtil;

@Mapper(componentModel = "spring", imports = {EnumFindUtil.class})
public interface ExerciseMapper {
	@Mappings({
		@Mapping(target = "oauthId", source = "exerciseAnalysisData.oauthId"),
		@Mapping(target = "createdAt", source = "exerciseAnalysisData.createdAt"),
		@Mapping(target = "needStepByTTS", source = "exerciseAnalysisData.needStepByTTS"),
		@Mapping(target = "needStepByLastWeek", source = "exerciseAnalysisData.needStepByLastWeek"),
		@Mapping(target = "content", source = "guide"),
		@Mapping(target = "comparedToLastWeek", source = "comparedToLastWeek")
	})
	ExerciseGuide toDocument(ExerciseAnalysisData exerciseAnalysisData, String guide, String comparedToLastWeek);

	@Mappings({
		@Mapping(target = "oauthId", source = "exerciseAnalysisData.oauthId"),
		@Mapping(target = "createdAt", source = "exerciseAnalysisData.createdAt"),
		@Mapping(target = "needStepByTTS", source = "exerciseAnalysisData.needStepByTTS"),
		@Mapping(target = "needStepByLastWeek", source = "exerciseAnalysisData.needStepByLastWeek"),
		@Mapping(target = "exerciseCalories", source = "exerciseCalorie")
	})
	ExerciseGuide toDocument(ExerciseAnalysisData exerciseAnalysisData, List<ExerciseCalorie> exerciseCalorie);

	ExerciseGuideResponse toResponse(ExerciseGuide exerciseGuide);

}