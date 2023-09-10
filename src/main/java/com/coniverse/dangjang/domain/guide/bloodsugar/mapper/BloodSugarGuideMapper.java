package com.coniverse.dangjang.domain.guide.bloodsugar.mapper;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import com.coniverse.dangjang.domain.analysis.dto.healthMetric.BloodSugarAnalysisData;
import com.coniverse.dangjang.domain.guide.bloodsugar.document.BloodSugarGuide;
import com.coniverse.dangjang.domain.guide.bloodsugar.document.SubGuide;
import com.coniverse.dangjang.domain.guide.bloodsugar.dto.BloodSugarGuideResponse;
import com.coniverse.dangjang.domain.guide.bloodsugar.dto.SubGuideResponse;
import com.coniverse.dangjang.domain.guide.bloodsugar.guideformat.format.GuideFormat;

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
	BloodSugarGuide toDocument(BloodSugarAnalysisData data);

	/**
	 * 혈당 가이드를 혈당 가이드 response로 변환
	 *
	 * @since 1.0.0
	 */
	@Mapping(target = "guides", source = "subGuides")
	BloodSugarGuideResponse toResponse(BloodSugarGuide guide);

	/**
	 * 혈당 분석 데이터를 혈당 서브 가이드로 변환
	 *
	 * @since 1.0.0
	 */
	@Mappings({
		@Mapping(target = "alert", source = "data.alert.title"),
		@Mapping(target = "title", source = "format.title"),
		@Mapping(target = "content", source = "format.content")
	})
	SubGuide toSubGuide(BloodSugarAnalysisData data, GuideFormat format);

	/**
	 * 혈당 서브 가이드를 단위가 없는 서브 가이드 response로 변환
	 *
	 * @since 1.0.0
	 */
	@Mapping(target = "unit", ignore = true)
	@Mapping(target = "type", source = "type.title")
	SubGuideResponse toSubGuideResponseNoUnit(SubGuide guide);

	/**
	 * 혈당 서브 가이드를 서브 가이드 response로 변환
	 */
	@Named("toSubGuideResponse")
	@Mapping(target = "type", source = "type.title")
	SubGuideResponse toSubGuideResponse(SubGuide guide);

	/**
	 * 혈당 서브 가이드 리스트를 서브 가이드 response 리스트로 변환
	 * <p>
	 * 혈당 가이드 response에 서브 가이드 리스트를 추가하기 위해 사용
	 *
	 * @since 1.0.0
	 */
	@IterableMapping(qualifiedByName = "toSubGuideResponse")
	List<SubGuideResponse> toSubGuideResponses(List<SubGuide> guides);
}
