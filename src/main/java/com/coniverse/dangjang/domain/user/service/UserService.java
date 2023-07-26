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

import lombok.RequiredArgsConstructor;

/**
 * USER Service
 *
 * @author EVE
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
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
		// Optional<User> user = userRepository.findByUserId(oAuthInfoResponse.getUserId(), oAuthInfoResponse.getOAuthProvider());
		Optional<User> user = userRepository.findById(oAuthInfoResponse.getUserId());
		return new UserResponse(user.orElseThrow(NonExistentUserException::new).getOauthId(), user.get().getNickname());
	}

	/**
	 * 새로운 유저 Oauth 사용자 정보 가져오기
	 *
	 * @param provider    카카오,네이버
	 * @param accessToken Oauth 토큰
	 * @return OAuthInfoResponse 유저 정보
	 * @throws IllegalArgumentException 잘못된 provider 일때 발생하는 오류
	 * @since 1.0
	 */

	public OAuthInfoResponse getOauthInfo(String provider, String accessToken) {
		if (provider.equals("kakao")) {
			KakaoLoginRequest kakaoLoginRequest = new KakaoLoginRequest();
			kakaoLoginRequest.setAccessToken(accessToken);
			return productOauthInfoService.request(kakaoLoginRequest);

		} else if (provider.equals("naver")) {
			NaverLoginRequest naverLoginRequest = new NaverLoginRequest();
			naverLoginRequest.setAccessToken(accessToken);
			return productOauthInfoService.request(naverLoginRequest);
		} else {
			throw new IllegalArgumentException("잘못된 provider 입니다.");
		}
	}

	public int calCulateRecommendedCalorie(Gender gender, int height, ActivityAmount activityAmount) {
		Double standardWeight = 0.0;
		if (gender.equals(false)) {
			standardWeight = (Double)(Math.pow(height / 100, 2.0) * 22);
		} else {
			standardWeight = (Double)(Math.pow(height / 100, 2.0) * 21);
		}

		if (activityAmount.equals(ActivityAmount.LOW)) {
			return (int)(standardWeight * 25);
		} else if (activityAmount.equals(ActivityAmount.MEDIUM)) {
			return (int)(standardWeight * 30);
		} else {
			return (int)(standardWeight * 35);
		}
	}

	public LoginResponse signUp(SignUpRequest signUpRequest) {
		OAuthInfoResponse oAuthInfoResponse = getOauthInfo(signUpRequest.provider(), signUpRequest.accessToken());
		ActivityAmount activityAmount = ActivityAmount.of(signUpRequest.activityAmount());

		Gender gender = Gender.of(signUpRequest.gender());
		int recommendedCalorie = calCulateRecommendedCalorie(Gender.of(signUpRequest.gender()), signUpRequest.height(),
			ActivityAmount.of(signUpRequest.activityAmount()));

		User user = User.builder()
			.oauthId(oAuthInfoResponse.getUserId())
			.oauthProvider(oAuthInfoResponse.getOAuthProvider())
			.nickname(signUpRequest.nickname())
			.birthday(signUpRequest.birthday())
			.activityAmount(activityAmount)
			.gender(gender)
			.height(signUpRequest.height())
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

	/**
	 * 중복된 닉네임 확인
	 *
	 * @param nickname 확인이 필요한 닉네임
	 * @return 중복된 닉네임이면 false, 중복되지 않았으면 true를 담은 DuplicateNicknameResponse 객체 리턴
	 * @since 1.0
	 */

	public DuplicateNicknameResponse checkDublicateNickname(String nickname) {
		Optional<User> findNickname = userRepository.findByNickname(nickname);
		if (findNickname.isPresent()) {
			return new DuplicateNicknameResponse(false);
		} else {
			return new DuplicateNicknameResponse(true);
		}
	}

}
