package com.coniverse.dangjang.domain.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.auth.dto.AuthToken;
import com.coniverse.dangjang.domain.auth.dto.OauthProvider;
import com.coniverse.dangjang.domain.auth.dto.request.KakaoLoginRequest;
import com.coniverse.dangjang.domain.auth.dto.request.NaverLoginRequest;
import com.coniverse.dangjang.domain.auth.dto.response.LoginResponse;
import com.coniverse.dangjang.domain.auth.service.AuthTokenGenerator;
import com.coniverse.dangjang.domain.auth.service.OauthLoginService;
import com.coniverse.dangjang.domain.infrastructure.auth.dto.OAuthInfoResponse;
import com.coniverse.dangjang.domain.user.dto.DuplicateNicknameResponse;
import com.coniverse.dangjang.domain.user.dto.SignUpRequest;
import com.coniverse.dangjang.domain.user.dto.UserResponse;
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
	private final OauthLoginService oauthLoginService;
	private final AuthTokenGenerator authTokensGenerator;

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
	 * 회원가입
	 *
	 * @param signUpRequest 회원가입 정보
	 * @return LoginResponse 로그인 응답
	 * @since 1.0.0
	 */
	public LoginResponse signUp(SignUpRequest signUpRequest) {
		OAuthInfoResponse oAuthInfoResponse = getOauthInfo(OauthProvider.of(signUpRequest.provider()), signUpRequest.accessToken());

		ActivityAmount activityAmount = ActivityAmount.of(signUpRequest.activityAmount());

		Gender gender = Gender.of(signUpRequest.gender());

		int recommendedCalorie = calculateRecommendedCalorie(Gender.of(signUpRequest.gender()), signUpRequest.height(),
			ActivityAmount.of(signUpRequest.activityAmount()));

		User user = User.builder()
			.oauthId(oAuthInfoResponse.getOauthId())
			.oauthProvider(oAuthInfoResponse.getOauthProvider())
			.nickname(signUpRequest.nickname())
			.birthday(signUpRequest.birthday())
			.activityAmount(activityAmount)
			.gender(gender)
			.height(signUpRequest.height())
			.status(Status.ACTIVE)
			.recommendedCalorie(recommendedCalorie)
			.build();

		return signupAfterLogin(new UserResponse(userRepository.save(user).getOauthId(), user.getNickname()));
	}

	/**
	 * 새로운 유저 Oauth 사용자 정보 가져오기
	 *
	 * @param provider    카카오,네이버
	 * @param accessToken Oauth 토큰
	 * @return OAuthInfoResponse 유저 정보
	 * @throws IllegalArgumentException 잘못된 provider 일때 발생하는 오류
	 * @since 1.0.0
	 */

	public OAuthInfoResponse getOauthInfo(OauthProvider provider, String accessToken) {
		if (provider.equals(OauthProvider.KAKAO)) {
			KakaoLoginRequest kakaoLoginRequest = new KakaoLoginRequest(accessToken);
			return oauthLoginService.request(kakaoLoginRequest);

		} else if (provider.equals(OauthProvider.NAVER)) {
			NaverLoginRequest naverLoginRequest = new NaverLoginRequest(accessToken);
			return oauthLoginService.request(naverLoginRequest);
		} else {
			throw new IllegalArgumentException("잘못된 provider 입니다.");
		}
	}

	/**
	 * 권장 칼로리 계산하기
	 *
	 * @param gender         성별
	 * @param height         몸무게
	 * @param activityAmount 하루 활동량
	 * @return 권장칼로리
	 * @since 1.0.0
	 */

	public int calculateRecommendedCalorie(Gender gender, int height, ActivityAmount activityAmount) {
		double standardWeight;
		if (gender.equals(false)) {
			standardWeight = (Math.pow(height / 100.0, 2.0) * 22);
		} else {
			standardWeight = (Math.pow(height / 100.0, 2.0) * 21);
		}

		if (activityAmount.equals(ActivityAmount.LOW)) {
			return (int)(standardWeight * 25);
		} else if (activityAmount.equals(ActivityAmount.MEDIUM)) {
			return (int)(standardWeight * 30);
		} else {
			return (int)(standardWeight * 35);
		}
	}

	/**
	 * 회원가입 후 로그인
	 *
	 * @param userResponse 사용자 정보
	 * @return LoginResponse 로그인 정보
	 * @since 1.0.0
	 */
	public LoginResponse signupAfterLogin(UserResponse userResponse) {
		AuthToken authToken = authTokensGenerator.generate(userResponse.oauthId());
		return new LoginResponse(userResponse.nickname(), authToken.getAccessToken(), authToken.getRefreshToken(), false, false);
	}

	/**
	 * 중복된 닉네임 확인
	 *
	 * @param nickname 확인이 필요한 닉네임
	 * @return 중복된 닉네임이면 false, 중복되지 않았으면 true를 담은 DuplicateNicknameResponse 객체 리턴
	 * @since 1.0.0
	 */
	public DuplicateNicknameResponse checkDuplicatedNickname(String nickname) {
		Optional<User> findNickname = userRepository.findByNickname(nickname);
		if (findNickname.isPresent()) {
			return new DuplicateNicknameResponse(false);
		} else {
			return new DuplicateNicknameResponse(true);
		}
	}

}
