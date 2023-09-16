package com.coniverse.dangjang.domain.guide.exercise.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.coniverse.dangjang.domain.analysis.dto.healthMetric.ExerciseAnalysisData;
import com.coniverse.dangjang.domain.guide.exercise.document.ExerciseCalorie;
import com.coniverse.dangjang.domain.guide.exercise.document.ExerciseGuide;
import com.coniverse.dangjang.domain.guide.exercise.dto.ExerciseGuideResponse;

/**
 * 운동 가이드 Mapper
 *
 * @author EVE
 * @since 1.0.0
 */
@Mapper(componentModel = "spring")
public interface ExerciseGuideMapper {
	@Named("changeDateToUTC")
	static LocalDateTime changeDateToUTC(LocalDate createdAt) {
		ZonedDateTime zonedDateTime = ZonedDateTime.of(createdAt.atTime(9, 0, 0), ZoneId.systemDefault());
		// LocalDateTime를 UTC 시간대로 변환
		ZonedDateTime utcZonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
		// UTC 시간대의 LocalDate로 변환
		LocalDateTime utcLocalDate = utcZonedDateTime.toLocalDateTime();
		utcLocalDate = utcLocalDate.plusHours(9);
		return utcLocalDate;
	}

	@Named("changeDateToLocalDate")
	static LocalDate changeDateToLocalDate(LocalDateTime createdAt) {
		return createdAt.toLocalDate();

	}

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
	@Mapping(target = "stepCount", source = "exerciseAnalysisData.unit")
	@Mapping(target = "createdAt", source = "exerciseAnalysisData.createdAt", qualifiedByName = "changeDateToUTC")
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
	@Mapping(target = "stepCount", ignore = true)
	@Mapping(target = "createdAt", source = "exerciseAnalysisData.createdAt", qualifiedByName = "changeDateToUTC")
	ExerciseGuide toDocument(ExerciseAnalysisData exerciseAnalysisData, List<ExerciseCalorie> exerciseCalories);

	/**
	 * 운동 가이드 document를 response로 변경
	 *
	 * @param exerciseGuide 운동 가이드
	 * @return ExerciseGuideResponse
	 * @since 1.0.0
	 */
	@Mapping(target = "stepsCount", source = "stepCount")
	@Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "changeDateToLocalDate")
	ExerciseGuideResponse toResponse(ExerciseGuide exerciseGuide);

}