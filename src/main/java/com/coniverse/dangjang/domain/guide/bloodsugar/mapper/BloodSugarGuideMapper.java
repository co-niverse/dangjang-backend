package com.coniverse.dangjang.domain.guide.bloodsugar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.coniverse.dangjang.domain.analysis.dto.healthMetric.BloodSugarAnalysisData;
import com.coniverse.dangjang.domain.guide.bloodsugar.document.BloodSugarGuide;
import com.coniverse.dangjang.domain.guide.bloodsugar.document.SubGuide;
import com.coniverse.dangjang.domain.guide.bloodsugar.dto.BloodSugarGuideResponse;
import com.coniverse.dangjang.domain.guide.bloodsugar.dto.SubGuideResponse;
import com.coniverse.dangjang.domain.guide.bloodsugar.guideformat.GuideFormat;

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
	@Mapping(target = "alert", ignore = true)
	@Mapping(target = "title", ignore = true)
	@Mapping(target = "content", ignore = true)
	@Mapping(target = "summary", ignore = true)
	@Mapping(target = "unit", ignore = true)
	BloodSugarGuide toDocument(BloodSugarAnalysisData data);

	BloodSugarGuideResponse toResponse(BloodSugarGuide guide);

	/**
	 * 혈당 분석 데이터를 혈당 서브 가이드로 변환
	 *
	 * @since 1.0.0
	 */
	@Mapping(target = "alert", source = "data.alert.title")
	@Mapping(target = "title", source = "format.title")
	@Mapping(target = "content", source = "format.content")
	SubGuide toSubGuide(BloodSugarAnalysisData data, GuideFormat format);

	/**
	 * 혈당 서브 가이드를 서브 가이드 response로 변환
	 *
	 * @since 1.0.0
	 */
	@Mapping(target = "unit", ignore = true)
	SubGuideResponse toSubGuideResponse(SubGuide guide);
}
