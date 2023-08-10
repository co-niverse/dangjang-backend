package com.coniverse.dangjang.domain.healthmetric.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthMetricPatchRequest;
import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthMetricPostRequest;
import com.coniverse.dangjang.domain.healthmetric.dto.response.HealthMetricResponse;
import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.global.util.EnumFindUtil;

@Mapper(componentModel = "spring", imports = {EnumFindUtil.class, CommonCode.class})
public interface HealthMetricMapper {
	@Mappings({
		@Mapping(target = "type", expression = "java(EnumFindUtil.findByTitle(CommonCode.class, request.type()))"),
		@Mapping(target = "createdAt", source = "request.createdAt")
	})
	HealthMetric toEntity(HealthMetricPostRequest request, User user);

	@Mappings({
		@Mapping(target = "type", expression = "java(EnumFindUtil.findByTitle(CommonCode.class, request.newType()))"),
		@Mapping(target = "createdAt", source = "request.createdAt")
	})
	HealthMetric toEntity(HealthMetricPatchRequest request, User user);

	@Mapping(target = "type", source = "type.title")
	HealthMetricResponse toResponse(HealthMetric healthMetric);
}
