package com.coniverse.dangjang.domain.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.auth.dto.AuthToken.AuthToken;
import com.coniverse.dangjang.domain.auth.dto.Response.LoginResponse;
import com.coniverse.dangjang.domain.auth.dto.request.KakaoLoginRequest;
import com.coniverse.dangjang.domain.auth.dto.request.NaverLoginRequest;
import com.coniverse.dangjang.domain.auth.service.OauthInfoService;
import com.coniverse.dangjang.domain.auth.service.authToken.AuthTokensGenerator;
import com.coniverse.dangjang.domain.user.dto.DuplicateNicknameResponse;
import com.coniverse.dangjang.domain.user.dto.SignUpRequest;
import com.coniverse.dangjang.domain.user.dto.UserResponse;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.entity.enums.ActivityAmount;
import com.coniverse.dangjang.domain.user.entity.enums.Gender;
import com.coniverse.dangjang.domain.user.entity.enums.Status;
import com.coniverse.dangjang.domain.user.exception.NonExistentUserException;
import com.coniverse.dangjang.domain.user.infrastructure.OAuthInfoResponse;
import com.coniverse.dangjang.domain.user.repository.UserRepository;

import lombok.AllArgsConstructor;

/**
 * USER Service
 *
 * @author EVE
 * @since 1.0
 */
@Service
@AllArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final OauthInfoService productOauthInfoService;
	private final AuthTokensGenerator authTokensGenerator;

	/**
	 * 기존 유저 확인
	 *
	 * @param oAuthInfoResponse 카카오,네이버에서 사용자 정보 조회한 데이터 (authID ,Provider)
	 * @return userInfo 기존 유저일 때, 유저 정보를 리턴
	 * @throws NonExistentUserException 회원가입된 유저가 아닐때 발생하는 오류
	 * @since 1.0
	 */

	public UserResponse findUser(OAuthInfoResponse oAuthInfoResponse) throws NonExistentUserException {
		Optional<User> user = userRepository.findByUserId(oAuthInfoResponse.getUserId(), oAuthInfoResponse.getOAuthProvider());
		return new UserResponse(user.orElseThrow(NonExistentUserException::new).getOauthId(), user.get().getNickname());
	}

	/**
	 * 새로운 유저 회원가입
	 *
	 * @param signUpRequest 카카오,네이버에서 사용자 정보 조회한 데이터 (authID ,Provider)
	 * @return 새로 가입된 유저 회원가입
	 * @since 1.0
	 */

	public LoginResponse signUp(SignUpRequest signUpRequest) {
		OAuthInfoResponse oAuthInfoResponse = null;
		if (signUpRequest.getProvider().equals("kakao")) {
			KakaoLoginRequest kakaoLoginRequest = new KakaoLoginRequest();
			kakaoLoginRequest.setAccessToken(signUpRequest.getAccessToken());
			oAuthInfoResponse = productOauthInfoService.request(kakaoLoginRequest);

		} else if (signUpRequest.getProvider().equals("naver")) {
			NaverLoginRequest naverLoginRequest = new NaverLoginRequest();
			naverLoginRequest.setAccessToken(signUpRequest.getAccessToken());
			oAuthInfoResponse = productOauthInfoService.request(naverLoginRequest);
		} else {

		}

		Gender gender = null;
		Float standardWeight = 0f;
		if (signUpRequest.getGender().equals(false)) {
			gender = Gender.M;
			standardWeight = (float)(Math.pow(signUpRequest.getHeight() / 100, 2.0) * 22f);
		} else {
			gender = Gender.F;
			standardWeight = (float)(Math.pow(signUpRequest.getHeight() / 100, 2.0) * 21f);
		}

		int recommendedCalorie = 0;
		ActivityAmount activityAmount = ActivityAmount.LOW;
		if (signUpRequest.getActivityAmount().equals("LOW")) {
			activityAmount = ActivityAmount.LOW;
			recommendedCalorie = (int)(standardWeight * 25);
		} else if (signUpRequest.getActivityAmount().equals("MEDIUM")) {
			activityAmount = ActivityAmount.MEDIUM;
			recommendedCalorie = (int)(standardWeight * 30);
		} else {
			activityAmount = ActivityAmount.HIGH;
			recommendedCalorie = (int)(standardWeight * 35);
		}

		User user = User.builder()
			.oauthId(oAuthInfoResponse.getUserId())
			.oauthProvider(oAuthInfoResponse.getOAuthProvider())
			.nickname(signUpRequest.getNickname())
			.birthday(signUpRequest.getBirthday())
			.activityAmount(activityAmount)
			.gender(gender)
			.height(signUpRequest.getHeight())
			.profileImagePath("/")
			.status(Status.ACTIVE)
			.recommendedCalorie(recommendedCalorie)
			.build();

		return login(new UserResponse(userRepository.save(user).getOauthId(), user.getNickname()));
	}

	public LoginResponse login(UserResponse userResponse) {
		AuthToken authToken = authTokensGenerator.generate(userResponse.oauthId());
		return new LoginResponse(userResponse.nickname(), authToken.getAccessToken(), authToken.getRefreshToken(), false, false);
	}

	public DuplicateNicknameResponse checkDublicateNickname(String nickname) {
		System.out.println("nickname : " + nickname);
		Integer countNickname = userRepository.countByNickname(nickname);
		System.out.println("count : " + countNickname);
		if (countNickname > 0) {
			return new DuplicateNicknameResponse(false);
		} else {
			return new DuplicateNicknameResponse(true);
		}
	}

}
