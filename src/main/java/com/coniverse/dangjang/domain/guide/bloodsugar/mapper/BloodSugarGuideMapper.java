package com.coniverse.dangjang.domain.guide.bloodsugar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.coniverse.dangjang.domain.analysis.dto.healthMetric.BloodSugarAnalysisData;
import com.coniverse.dangjang.domain.guide.bloodsugar.document.BloodSugarGuide;
import com.coniverse.dangjang.domain.guide.bloodsugar.document.SubGuide;
import com.coniverse.dangjang.domain.guide.bloodsugar.dto.SubGuideResponse;

/**
 * 혈당 가이드 mapper
 *
 * @author TEO
 * @since 1.0.0
 */
@Mapper(componentModel = "spring")
public interface BloodSugarGuideMapper {
	/**
	 * 혈당 분석 데이터를 혈당 가이드 document로 변환
	 *
	 * @since 1.0.0
	 */
	@Mapping(target = "todayAlert", ignore = true)
	@Mapping(target = "todayContent", ignore = true)
	@Mapping(target = "summary", ignore = true)
	BloodSugarGuide toDocument(BloodSugarAnalysisData data);

	/**
	 * 혈당 분석 데이터를 혈당 서브 가이드로 변환
	 *
	 * @since 1.0.0
	 */
	SubGuide toSubGuide(BloodSugarAnalysisData data, String content);

	/**
	 * 혈당 서브 가이드를 서브 가이드 response로 변환
	 *
	 * @since 1.0.0
	 */
	@Mapping(target = "type", source = "type.title")
	@Mapping(target = "unit", ignore = true)
	@Mapping(target = "alert", source = "alert.title")
	SubGuideResponse toSubGuideResponse(SubGuide guide);
}
