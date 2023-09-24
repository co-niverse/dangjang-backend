package com.coniverse.dangjang.domain.point.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.point.entity.PointProduct;
import com.coniverse.dangjang.domain.point.enums.PointType;
import com.coniverse.dangjang.domain.point.repository.PointProductRepository;
import com.coniverse.dangjang.global.exception.InvalidTokenException;

import lombok.AllArgsConstructor;

/**
 * 포인트 관련 Search Serivce
 *
 * @author EVE
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class PointSearchService {
	private final PointProductRepository pointProductRepository;

	public PointProduct findPointProductById(String pointProduct) {
		return pointProductRepository.findById(pointProduct)
			.orElseThrow(() -> new InvalidTokenException(pointProduct + " 포인트 타입이 없습니다."));
	}

	public List<PointProduct> findAllByType(PointType type) {
		return pointProductRepository.findAllByType(type);
	}

}
