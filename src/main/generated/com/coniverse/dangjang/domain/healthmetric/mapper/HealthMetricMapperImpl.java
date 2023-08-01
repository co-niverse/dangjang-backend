package com.coniverse.dangjang.domain.healthmetric.mapper;

import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthMetricPatchRequest;
import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthMetricPostRequest;
import com.coniverse.dangjang.domain.healthmetric.dto.response.HealthMetricResponse;
import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.healthmetric.enums.HealthMetricType;
import com.coniverse.dangjang.domain.user.entity.User;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-01T15:11:31+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class HealthMetricMapperImpl implements HealthMetricMapper {

    @Override
    public HealthMetric toEntity(HealthMetricPostRequest request, LocalDate createdAt, User user) {
        if ( request == null && createdAt == null && user == null ) {
            return null;
        }

        HealthMetric.HealthMetricBuilder healthMetric = HealthMetric.builder();

        if ( request != null ) {
            healthMetric.unit( request.unit() );
        }
        healthMetric.createdAt( createdAt );
        healthMetric.user( user );
        healthMetric.healthMetricType( HealthMetricType.findByTitle(request.healthMetricType()) );

        return healthMetric.build();
    }

    @Override
    public HealthMetric toEntity(HealthMetricPatchRequest request, LocalDate createdAt, User user) {
        if ( request == null && createdAt == null && user == null ) {
            return null;
        }

        HealthMetric.HealthMetricBuilder healthMetric = HealthMetric.builder();

        if ( request != null ) {
            healthMetric.unit( request.unit() );
        }
        healthMetric.createdAt( createdAt );
        healthMetric.user( user );
        healthMetric.healthMetricType( HealthMetricType.findByTitle(request.newHealthMetricType()) );

        return healthMetric.build();
    }

    @Override
    public HealthMetricResponse toResponse(HealthMetric healthMetric) {
        if ( healthMetric == null ) {
            return null;
        }

        HealthMetricType healthMetricType = null;
        LocalDate createdAt = null;
        String unit = null;

        healthMetricType = healthMetric.getHealthMetricType();
        createdAt = healthMetric.getCreatedAt();
        unit = healthMetric.getUnit();

        HealthMetricResponse healthMetricResponse = new HealthMetricResponse( healthMetricType, createdAt, unit );

        return healthMetricResponse;
    }
}
