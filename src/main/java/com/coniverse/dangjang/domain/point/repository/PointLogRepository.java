package com.coniverse.dangjang.domain.point.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coniverse.dangjang.domain.point.entity.PointId;
import com.coniverse.dangjang.domain.point.entity.PointLog;

/**
 * Point Repository
 *
 * @author EVE
 * @since 1.0.0
 */
public interface PointLogRepository extends JpaRepository<PointLog, PointId> {

}
