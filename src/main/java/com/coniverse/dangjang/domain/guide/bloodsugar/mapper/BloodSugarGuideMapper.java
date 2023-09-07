package com.coniverse.dangjang.domain.guide.bloodsugar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.coniverse.dangjang.domain.analysis.dto.healthMetric.BloodSugarAnalysisData;
import com.coniverse.dangjang.domain.guide.bloodsugar.document.BloodSugarGuide;
import com.coniverse.dangjang.domain.guide.bloodsugar.dto.BloodSugarGuideResponse;

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
	@Mapping(target = "id", ignore = true)
	BloodSugarGuide toDocument(BloodSugarAnalysisData data, String content, String summary);

	/**
	 * document를 response로 변환
	 *
	 * @since 1.0.0
	 */
	@Mapping(target = "type", source = "type.title")
	BloodSugarGuideResponse toResponse(BloodSugarGuide guide);
}
