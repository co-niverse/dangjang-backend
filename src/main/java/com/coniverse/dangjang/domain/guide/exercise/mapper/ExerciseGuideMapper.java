package com.coniverse.dangjang.domain.guide.exercise.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.coniverse.dangjang.domain.analysis.dto.healthMetric.ExerciseAnalysisData;
import com.coniverse.dangjang.domain.guide.exercise.document.ExerciseGuide;
import com.coniverse.dangjang.domain.guide.exercise.dto.ExerciseCalorie;
import com.coniverse.dangjang.domain.guide.exercise.dto.ExerciseGuideResponse;

/**
 * 운동 가이드 Mapper
 *
 * @author EVE
 * @since 1.0.0
 */
@Mapper(componentModel = "spring")
public interface ExerciseGuideMapper {

	/**
	 * 운동 가이드(걸음수) document 생성
	 *
	 * @param exerciseAnalysisData 운동 분석 데이터
	 * @param content              운동 가이드 내용
	 * @param comparedToLastWeek   저번주 대비 걸음수 가이드 내용
	 * @return WeightGuide
	 * @since 1.0.0
	 */
	@Mapping(target = "exerciseCalories", ignore = true)
	ExerciseGuide toDocument(ExerciseAnalysisData exerciseAnalysisData, String content, String comparedToLastWeek);

	/**
	 * 운동 가이드(칼로리) document 생성
	 *
	 * @param exerciseAnalysisData 운동 분석 데이터
	 * @param exerciseCalories     운동 칼로리
	 * @return WeightGuide
	 * @since 1.0.0
	 */
	@Mapping(target = "content", ignore = true)
	@Mapping(target = "comparedToLastWeek", ignore = true)
	ExerciseGuide toDocument(ExerciseAnalysisData exerciseAnalysisData, List<ExerciseCalorie> exerciseCalories);

	/**
	 * 운동 가이드 document를 response로 변경
	 *
	 * @param exerciseGuide 운동 가이드
	 * @return ExerciseGuideResponse
	 * @since 1.0.0
	 */
	ExerciseGuideResponse toResponse(ExerciseGuide exerciseGuide);

}