package com.coniverse.dangjang.domain.point.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.point.entity.PointProduct;
import com.coniverse.dangjang.domain.point.entity.UserPoint;
import com.coniverse.dangjang.domain.point.enums.PointType;
import com.coniverse.dangjang.domain.point.repository.PointProductRepository;
import com.coniverse.dangjang.domain.point.repository.UserPointRepository;
import com.coniverse.dangjang.global.exception.InvalidPointException;

import lombok.RequiredArgsConstructor;

/**
 * 포인트 관련 Search Service
 *
 * @author EVE
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class PointSearchService {
	private final PointProductRepository pointProductRepository;
	private final UserPointRepository userPointRepository;

	/**
	 * 포인트 상품 조회
	 *
	 * @param productName 포인트 상품 이름
	 * @since 1.0.0
	 */
	public PointProduct findPointProductById(String productName) {
		return pointProductRepository.findById(productName)
			.orElseThrow(() -> new InvalidPointException("포인트 상품이 없습니다."));
	}

	/**
	 * type에 따른 포인트 상품 목록 조회
	 *
	 * @param type 포인트 상품 타입
	 * @since 1.0.0
	 */
	public List<PointProduct> findAllByType(PointType type) {
		return pointProductRepository.findAllByType(type);
	}

	/**
	 * 사용자 포인트 조회
	 *
	 * @param oauthId 사용자 아이디
	 * @since 1.0.0
	 */
	public UserPoint findUserPointByOauthId(String oauthId) {
		return userPointRepository.findById(oauthId)
			.orElseThrow(() -> new InvalidPointException("유저의 포인트 테이블이 없습니다."));
	}

}
