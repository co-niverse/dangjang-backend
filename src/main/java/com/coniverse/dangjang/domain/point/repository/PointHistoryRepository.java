package com.coniverse.dangjang.domain.point.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coniverse.dangjang.domain.point.entity.PointHistory;
import com.coniverse.dangjang.domain.point.entity.PointId;

/**
 * Point Repository
 *
 * @author EVE
 * @since 1.0.0
 */
public interface PointHistoryRepository extends JpaRepository<PointHistory, PointId> {

}
