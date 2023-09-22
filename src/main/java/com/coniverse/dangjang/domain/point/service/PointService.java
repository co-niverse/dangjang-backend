package com.coniverse.dangjang.domain.point.service;

import java.time.LocalDate;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.auth.service.DefaultOauthLoginService;
import com.coniverse.dangjang.domain.point.entity.Point;
import com.coniverse.dangjang.domain.point.enums.PointType;
import com.coniverse.dangjang.domain.point.mapper.PointMapper;
import com.coniverse.dangjang.domain.point.repository.PointRepository;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.repository.UserRepository;
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
	private final PointRepository pointRepository;
	private final UserRepository userRepository;
	private final DefaultOauthLoginService defaultOauthLoginService;
	private final PointMapper pointMapper;

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
		User user = userRepository.findByOauthId(oauthId)
			.orElseThrow(() -> new InvalidTokenException("존재하지 않는 사용자 입니다."));
		if (!user.getAccessedAt().equals(LocalDate.now())) {
			defaultOauthLoginService.updateUserAccessedAt(user);
			addPointEvent(PointType.ACCESS, user);
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
	@Async
	public void addSignupPoint(String oauthId) {
		User user = userRepository.findByOauthId(oauthId)
			.orElseThrow(() -> new InvalidTokenException("존재하지 않는 사용자 입니다."));
		if (user.getCreatedAt().toLocalDate().equals(LocalDate.now())) {
			addPointEvent(PointType.REGISTER, user);
		}
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
	private void addPointEvent(PointType pointType, User user) {
		int balancePoint = user.getPoint() + pointType.getChangePoint();
		Point savedPoint = pointRepository.save(pointMapper.toEntity(pointType, user, balancePoint));
		userRepository.updatePointByOauthId(user.getOauthId(), savedPoint.getBalancePoint());
	}
}
