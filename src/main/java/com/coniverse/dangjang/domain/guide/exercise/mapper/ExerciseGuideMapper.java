package com.coniverse.dangjang.domain.guide.exercise.mapper;

import java.time.LocalDate;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.coniverse.dangjang.domain.analysis.dto.healthMetric.ExerciseAnalysisData;
import com.coniverse.dangjang.domain.guide.exercise.document.ExerciseCalorie;
import com.coniverse.dangjang.domain.guide.exercise.document.ExerciseGuide;
import com.coniverse.dangjang.domain.guide.exercise.dto.ExerciseGuideResponse;
import com.coniverse.dangjang.global.util.LocalDateChangeUtil;

/**
 * 운동 가이드 Mapper
 *
 * @author EVE
 * @since 1.0.0
 */
@Mapper(componentModel = "spring")
public interface ExerciseGuideMapper {
	LocalDateChangeUtil localDateChangeUtil = new LocalDateChangeUtil();

	/**
	 * LocalDate를 UTC 날짜와 동일하게 맞춘다
	 * <p>
	 * 현재 KST 날짜보다 하루를 더 추가해 , UTC로 변경되었을 때 동일한 날짜가 되도록 한다.
	 *
	 * @param createdAt 생성일
	 * @return LocalDate UTC 날짜와 동일한 날짜
	 * @since 1.0.0
	 */
	@Named("changeDateToUTC")
	static LocalDate changeDateToUTC(LocalDate createdAt) {
		return localDateChangeUtil.convertDateToUTC(createdAt);
	}

	/**
	 * UCT 날짜를 LocalDate와 동일하게 맞춘다
	 * <p>
	 * MongoDB에서 date를 가져와 KST로 변경되었을 때 하루가 더 많음으로 , 다시 하루를 되돌려준다.
	 *
	 * @param createdAt 생성일
	 * @return LocalDate UTC 날짜와 동일한 날짜
	 * @since 1.0.0
	 */
	@Named("changeDateToLocalDate")
	static LocalDate changeDateToLocalDate(LocalDate createdAt) {
		return localDateChangeUtil.convertDateToKST(createdAt);

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