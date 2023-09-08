package com.coniverse.dangjang.domain.healthmetric.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;

/**
 * 건강지표 repository
 *
 * @author TEO , EVE
 * @Method Name : findByHealthMetricId(String oauthId, CommonCode commonCode) 최근 마지막 건강지표 조회
 * @Method Name : findByHealthMetricId(String oauthId, LocalDate createdAt, CommonCode commonCode) 해당 날짜 건강지표 조회
 * @since 1.0.0
 */
public interface HealthMetricRepository extends JpaRepository<HealthMetric, Long> {
	// @Query("SELECT h FROM HealthMetric h WHERE h.healthMetricId.oauthId = ?1 AND h.healthMetricId.createdAt = ?2 AND h.healthMetricId.commonCode = ?3")
	// Optional<HealthMetric> findByHealthMetricId(String oauthId, LocalDate createdAt, CommonCode commonCode);

	@Query("SELECT h FROM HealthMetric h WHERE h.healthMetricId.oauthId = ?1 AND h.healthMetricId.createdAt = ?2 AND h.healthMetricId.type = ?3")
	Optional<HealthMetric> findByHealthMetricId(String oauthId, LocalDate createdAt, CommonCode type);

	@Query("SELECT h FROM HealthMetric h WHERE h.healthMetricId.oauthId = ?1 AND h.healthMetricId.type = ?2 order by h.healthMetricId.createdAt desc limit 1")
	Optional<HealthMetric> findByHealthMetricId(String oauthId, CommonCode commonCode);

	@Query("SELECT h FROM HealthMetric h WHERE h.healthMetricId.oauthId = ?1 AND h.healthMetricId.type = ?2 and h.healthMetricId.createdAt between ?3 and ?4")
	List<HealthMetric> findLastWeekByHealthMetricId(String oauthId, CommonCode commonCode, LocalDate startDate, LocalDate endDate);
}
