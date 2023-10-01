package com.coniverse.dangjang.domain.point.service;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.auth.service.DefaultOauthLoginService;
import com.coniverse.dangjang.domain.healthmetric.enums.HealthConnect;
import com.coniverse.dangjang.domain.point.dto.request.UsePointRequest;
import com.coniverse.dangjang.domain.point.dto.response.ProductsResponse;
import com.coniverse.dangjang.domain.point.dto.response.UsePointResponse;
import com.coniverse.dangjang.domain.point.entity.PointLog;
import com.coniverse.dangjang.domain.point.entity.PointProduct;
import com.coniverse.dangjang.domain.point.entity.UserPoint;
import com.coniverse.dangjang.domain.point.enums.EarnPoint;
import com.coniverse.dangjang.domain.point.enums.PointType;
import com.coniverse.dangjang.domain.point.mapper.PointMapper;
import com.coniverse.dangjang.domain.point.repository.PointLogRepository;
import com.coniverse.dangjang.domain.point.repository.UserPointRepository;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.repository.UserRepository;
import com.coniverse.dangjang.domain.user.service.UserSearchService;
import com.coniverse.dangjang.global.exception.InvalidTokenException;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

/**
 * 포인트 관련 서비스
 *
 * @author EVE
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
@Transactional
public class PointService {
	private final PointLogRepository pointLogRepository;
	private final UserRepository userRepository;
	private final PointMapper pointMapper;
	private final UserSearchService userSearchService;
	private final PointSearchService pointSearchService;
	private final DefaultOauthLoginService defaultOauthLoginService;
	private final UserPointRepository userPointRepository;

	/**
	 * 1일 1접속 포인트 적립
	 * <p>
	 * 1일 1접속 가능 유저인지 자격을 확인하고,
	 * 가능한 유저에게 포인트를 적립한다.
	 *
	 * @param oauthId 유저 아이디
	 * @since 1.0.0
	 */
	@Async
	public void addAccessPoint(String oauthId) {
		User user = userSearchService.findUserByOauthId(oauthId);
		if (!user.getAccessedAt().equals(LocalDate.now())) {
			addPointEvent(EarnPoint.ACCESS.getTitle(), user);
			defaultOauthLoginService.updateUserAccessedAt(user);
		}
	}

	/**
	 * 회원가입 포인트 적립
	 * <p>
	 * 회원가입 유저인지 자격을 확인하고, 포인트를 적립한다.
	 *
	 * @param oauthId 유저 아이디
	 * @since 1.0.0
	 */

	public void addSignupPoint(String oauthId) {
		User user = userSearchService.findUserByOauthId(oauthId);
		if (user.getCreatedAt().toLocalDate().equals(LocalDate.now())) {
			userPointRepository.save(pointMapper.toEntity(user.getOauthId(), 0));
			addPointEvent(EarnPoint.REGISTER.getTitle(), user);
		}
	}

	/**
	 * 헬스커넥트 포인트 적립
	 * <p>
	 * 헬스커넥트 연동 유저인지 자격을 확인하고, 포인트를 적립한다.
	 *
	 * @param user 유저
	 * @since 1.0.0
	 */
	public void addHealthConnectPoint(User user) {
		if (user.getHealthConnect().equals(HealthConnect.CONNECTING)) {
			addPointEvent(EarnPoint.HEALTH_CONNECT.getTitle(), user);
		}
	}

	/**
	 * 헬스커넥트 포인트 적립
	 * <p>
	 * 헬스커넥트 연동 유저인지 자격을 확인하고, 포인트를 적립한다.
	 *
	 * @param oauthId 유저 아이디
	 * @param request 포인트 사용 요청 객체
	 * @since 1.0.0
	 */

	public UsePointResponse purchaseProduct(String oauthId, UsePointRequest request) {
		User user = userSearchService.findUserByOauthId(oauthId);
		PointLog savedPointLog;
		try {
			savedPointLog = addPointEvent(request.type(), user);
		} catch (IllegalArgumentException e) {
			throw new InvalidTokenException("%s의 포인트 상품이 존재하지 않습니다.".formatted(request.type()));
		}
		return new UsePointResponse(request.phone(), savedPointLog.getProduct(), savedPointLog.getChangePoint(), savedPointLog.getBalancePoint());
	}

	/**
	 * 포인트 이벤트 추가
	 * <p>
	 * 포인트 적립 및 사용 이벤트가 발생했을 때,
	 * 포인트 이벤트를 추가한다.
	 *
	 * @param user 유저
	 * @since 1.0.0
	 */
	private PointLog addPointEvent(String pointProduct, User user) {
		PointProduct product = pointSearchService.findPointProductById(pointProduct);
		UserPoint userPoint = pointSearchService.findUserPointByOauthId(user.getId());
		int changePoint = getChangePoint(product);
		int balancePoint = getBalancePoint(changePoint, userPoint.getPoint());
		PointLog savedPointLog = pointLogRepository.save(pointMapper.toEntity(product, user, changePoint, balancePoint));
		userPoint.setPoint(savedPointLog.getBalancePoint());
		return savedPointLog;
	}

	/**
	 * pointProduct 에 따른 포인트 변동량 조회
	 *
	 * @param product 포인트 상품
	 * @since 1.0.0
	 */
	private int getChangePoint(PointProduct product) {
		if (product.getType().equals(PointType.USE)) {
			return -product.getPoint();
		}
		return product.getPoint();
	}

	/**
	 * 최종 point 계산
	 * <p>
	 * 만약 최종 계산한 point가 0보다 적다면 오류를 반환한다.
	 *
	 * @param changePoint  포인트 변동량
	 * @param balancePoint 기존 포인트
	 * @throws InvalidTokenException 포인트 부족 에러
	 * @since 1.0.0
	 */
	private int getBalancePoint(int changePoint, int balancePoint) {
		balancePoint += changePoint;
		if (balancePoint > 0) {
			return balancePoint;
		} else {
			throw new InvalidTokenException("포인트가 부족합니다.");
		}
	}

	/**
	 * 구매 가능 기프티콘 조회
	 * <p>
	 * 유저의 현재 포인트와 구매 가능한 기
	 *
	 * @param oauthId 유저 아이디
	 * @since 1.0.0
	 */

	public ProductsResponse getProducts(String oauthId) {
		int balancePoint = pointSearchService.findUserPointByOauthId(oauthId).getPoint();
		Map<String, Integer> products = pointSearchService.findAllByType(PointType.USE)
			.stream()
			.collect(Collectors.toMap(PointProduct::getProduct, PointProduct::getPoint));

		return new ProductsResponse(balancePoint, products);

	}
}
