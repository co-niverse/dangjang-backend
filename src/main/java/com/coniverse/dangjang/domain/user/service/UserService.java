package com.coniverse.dangjang.domain.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.user.dto.SignUpRequest;
import com.coniverse.dangjang.domain.user.dto.Status;
import com.coniverse.dangjang.domain.user.dto.UserInfo;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.entity.UserId;
import com.coniverse.dangjang.domain.user.exception.NonExistentUserException;
import com.coniverse.dangjang.domain.user.infrastructure.OAuthInfoResponse;
import com.coniverse.dangjang.domain.user.repository.UserRepository;

/**
 * USER Service
 *
 * @author EVE
 * @since 1.0
 */
@Service
public class UserService {
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * 기존 유저 확인
	 *
	 * @param oAuthInfoResponse 카카오,네이버에서 사용자 정보 조회한 데이터 (authID ,Provider)
	 * @return userInfo 기존 유저일 때, 유저 정보를 리턴
	 * @throws NonExistentUserException 회원가입된 유저가 아닐때 발생하는 오류
	 * @since 1.0
	 */

	public UserInfo findUser(OAuthInfoResponse oAuthInfoResponse) throws NonExistentUserException {

		Optional<User> user = userRepository.findByOauthId(oAuthInfoResponse.getUserId());

		if (user.isPresent()) {
			return new UserInfo(user.get().getUserId().getOauthId(), user.get().getNickName());
		} else {
			throw new NonExistentUserException();
		}
	}

	/**
	 * 새로운 유저 회원가입
	 *
	 * @param signUpRequest 카카오,네이버에서 사용자 정보 조회한 데이터 (authID ,Provider)
	 * @return 새로 가입된 유저 회원가입
	 * @since 1.0
	 */

	public UserId signUp(SignUpRequest signUpRequest) {
		User user = User.builder()
			.userId(new UserId("", "kakao"))
			.nickname(signUpRequest.getNickname())
			.activityAmount(signUpRequest.getActivityAmount())
			.birthday(signUpRequest.getBirthday())
			.diabetes(signUpRequest.getDiabetes())
			.diabetes_year(signUpRequest.getDiabetes_year())
			.height(signUpRequest.getHeight())
			.injection(signUpRequest.getInjection())
			.medicine(signUpRequest.getMedicine())
			.recommended_calorie(signUpRequest.getRecommended_calorie())
			.status(Status.ACTIVE)
			.imagePath("")
			.build();

		return userRepository.save(user).getUserId();
	}
}
