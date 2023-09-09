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

/**
 * 운동 가이드 Mapper
 *
 * @author EVE
 * @since 1.0.0
 */
@Mapper(componentModel = "spring", imports = {EnumFindUtil.class})
public interface ExerciseMapper {

	/**
	 * 운동 가이드(걸음수) Document 생성
	 *
	 * @param exerciseAnalysisData 운동 분석 데이터
	 * @param guide                운동 가이드 내용
	 * @param comparedToLastWeek   저번주 대비 걸음수 가이드 내용
	 * @return WeightGuide
	 * @since 1.0.0
	 */
	@Mappings({
		@Mapping(target = "oauthId", source = "exerciseAnalysisData.oauthId"),
		@Mapping(target = "createdAt", source = "exerciseAnalysisData.createdAt"),
		@Mapping(target = "needStepByTTS", source = "exerciseAnalysisData.needStepByTTS"),
		@Mapping(target = "needStepByLastWeek", source = "exerciseAnalysisData.needStepByLastWeek"),
		@Mapping(target = "content", source = "guide"),
		@Mapping(target = "comparedToLastWeek", source = "comparedToLastWeek")
	})
	ExerciseGuide toDocument(ExerciseAnalysisData exerciseAnalysisData, String guide, String comparedToLastWeek);

	/**
	 * 운동 가이드(칼로리) Document 생성
	 *
	 * @param exerciseAnalysisData 운동 분석 데이터
	 * @param exerciseCalorie      운동 칼로리
	 * @return WeightGuide
	 * @since 1.0.0
	 */
	@Mappings({
		@Mapping(target = "oauthId", source = "exerciseAnalysisData.oauthId"),
		@Mapping(target = "createdAt", source = "exerciseAnalysisData.createdAt"),
		@Mapping(target = "needStepByTTS", source = "exerciseAnalysisData.needStepByTTS"),
		@Mapping(target = "needStepByLastWeek", source = "exerciseAnalysisData.needStepByLastWeek"),
		@Mapping(target = "exerciseCalories", source = "exerciseCalorie")
	})
	ExerciseGuide toDocument(ExerciseAnalysisData exerciseAnalysisData, List<ExerciseCalorie> exerciseCalorie);

	/**
	 * 운동 가이드 Document를  Response로 변경
	 *
	 * @param exerciseGuide 운동 가이드
	 * @return ExerciseGuideResponse
	 * @since 1.0.0
	 */
	ExerciseGuideResponse toResponse(ExerciseGuide exerciseGuide);

}