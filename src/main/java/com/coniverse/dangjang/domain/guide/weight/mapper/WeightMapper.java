package com.coniverse.dangjang.domain.guide.weight.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.coniverse.dangjang.domain.analysis.dto.healthMetric.WeightAnalysisData;
import com.coniverse.dangjang.domain.guide.weight.document.WeightGuide;
import com.coniverse.dangjang.domain.guide.weight.dto.WeightGuideResponse;

/**
 * 체중 가이드 Mapper
 *
 * @author EVE
 * @since 1.0.0
 */
@Mapper(componentModel = "spring")
public interface WeightMapper {
	/**
	 * 체중 가이드 Document 생성
	 *
	 * @param data 체중 분석 데이터
	 * @param content            체중 가이드 내용
	 * @return WeightGuide
	 * @since 1.0.0
	 */
	@Mapping(target = "title", source = "data.alert.title")
	WeightGuide toDocument(WeightAnalysisData data, String content);

	/**
	 * 체중 가이드 Document를  Response로 변경
	 *
	 * @param weightGuide 체중 가이드
	 * @return WeightGuideResponse
	 * @since 1.0.0
	 */
	@Mapping(target = "content", source = "weightGuide.content")
	WeightGuideResponse toResponse(WeightGuide weightGuide);
}
