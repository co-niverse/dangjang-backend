package com.coniverse.dangjang.domain.healthMetric.mapper;

import java.time.LocalDate;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.coniverse.dangjang.domain.healthMetric.dto.request.HealthMetricPatchRequest;
import com.coniverse.dangjang.domain.healthMetric.dto.request.HealthMetricPostRequest;
import com.coniverse.dangjang.domain.healthMetric.dto.response.HealthMetricResponse;
import com.coniverse.dangjang.domain.healthMetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.user.entity.User;

@Mapper(componentModel = "spring")
public interface HealthMetricMapper {
	@Mappings({
		@Mapping(target = "healthMetricType", expression = "java(HealthMetricType.findByTitle(request.healthMetricType()))"),
		@Mapping(target = "createdAt", source = "createdAt")
	})
	HealthMetric toEntity(HealthMetricPostRequest request, LocalDate createdAt, User user);

	@Mappings({
		@Mapping(target = "healthMetricType", expression = "java(HealthMetricType.findByTitle(request.newHealthMetricType()))"),
		@Mapping(target = "createdAt", source = "createdAt")
	})
	HealthMetric toEntity(HealthMetricPatchRequest request, LocalDate createdAt, User user);

	HealthMetricResponse toResponse(HealthMetric healthMetric);
}
