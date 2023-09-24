package com.coniverse.dangjang.domain.point.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coniverse.dangjang.domain.point.entity.PointProduct;
import com.coniverse.dangjang.domain.point.enums.PointType;

/**
 * 포인트 상품 Repository
 *
 * @author EVE
 * @since 1.0.0
 */
public interface PointProductRepository extends JpaRepository<PointProduct, String> {
	List<PointProduct> findAllByType(PointType type);
}
