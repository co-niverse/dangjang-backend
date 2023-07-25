package com.coniverse.dangjang.domain.healthMetric.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coniverse.dangjang.domain.healthMetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.healthMetric.entity.HealthMetricId;

/**
 * 건강지표 repository
 *
 * @author TEO
 * @since 1.0.0
 */
public interface HealthMetricRepository extends JpaRepository<HealthMetric, Long> {
	Optional<HealthMetric> findByHealthMetricId(HealthMetricId healthMetricId);
}
