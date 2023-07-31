package com.coniverse.dangjang.domain.user.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.infrastructure.auth.dto.OAuthInfoResponse;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.entity.enums.ActivityAmount;
import com.coniverse.dangjang.domain.user.entity.enums.Gender;
import com.coniverse.dangjang.domain.user.entity.enums.Status;
import com.coniverse.dangjang.domain.user.exception.NonExistentUserException;
import com.coniverse.dangjang.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * USER Service
 *
 * @author EVE, TEO
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;

	/**
	 * PK로 유저를 조회한다.
	 *
	 * @param oauthId 유저 PK
	 * @return User 유저
	 * @throws NonExistentUserException 유저가 존재하지 않을 경우 발생하는 예외
	 * @since 1.0.0
	 */
	public User findUserByOauthId(String oauthId) {
		return userRepository.findById(oauthId).orElseThrow(NonExistentUserException::new);
	}

	/**
	 * 새로운 유저 회원가입
	 *
	 * @param oauthInfoResponse 카카오,네이버에서 사용자 정보 조회한 데이터 (authID ,Provider)
	 * @return 새로 가입된 유저 회원가입
	 * @since 1.0.0
	 */

	public String signUp(OAuthInfoResponse oauthInfoResponse) {
		User user = User.builder()
			.oauthId(oauthInfoResponse.getOauthId())
			.oauthProvider(oauthInfoResponse.getOauthProvider())
			.nickname("nickname")
			.gender(Gender.F)
			.height(180)
			.activityAmount(ActivityAmount.MEDIUM)
			.birthday(LocalDate.now())
			.role("ROLE_USER")
			.status(Status.ACTIVE)
			.recommendedCalorie(2000)
			.build();

		return userRepository.save(user).getOauthId();
	}
}
