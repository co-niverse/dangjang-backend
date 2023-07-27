package com.coniverse.dangjang.domain.healthMetric.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.coniverse.dangjang.domain.healthMetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.healthMetric.enums.HealthMetricType;

/**
 * 건강지표 repository
 *
 * @author TEO
 * @since 1.0.0
 */
public interface HealthMetricRepository extends JpaRepository<HealthMetric, Long> {
	@Query("SELECT h FROM HealthMetric h WHERE h.healthMetricId.oauthId = ?1 AND h.healthMetricId.createdAt = ?2 AND h.healthMetricId.healthMetricType = ?3")
	Optional<HealthMetric> findByHealthMetricId(String oauthId, LocalDate createdAt, HealthMetricType healthMetricType);
}
