package com.coniverse.dangjang.domain.point.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coniverse.dangjang.domain.point.entity.PointId;
import com.coniverse.dangjang.domain.point.entity.PurchaseHistory;

/**
 * 포인트 상품 구매 내역 Repository
 *
 * @author EVE
 * @since 1.0.0
 */
public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, PointId> {

}
