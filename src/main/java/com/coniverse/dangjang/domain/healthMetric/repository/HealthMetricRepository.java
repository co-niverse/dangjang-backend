package com.coniverse.dangjang.domain.healthMetric.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coniverse.dangjang.domain.healthMetric.entity.HealthMetric;

public interface HealthMetricRepository extends JpaRepository<HealthMetric, Long> {
}
