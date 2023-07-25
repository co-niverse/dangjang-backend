package com.coniverse.dangjang.domain.healthMetric.mapper;

import org.mapstruct.Mapper;

import com.coniverse.dangjang.domain.healthMetric.dto.response.HealthMetricResponse;
import com.coniverse.dangjang.domain.healthMetric.entity.HealthMetric;

@Mapper(componentModel = "spring")
public interface BloodSugarMapper {
	HealthMetricResponse toResponse(HealthMetric healthMetric);
}
