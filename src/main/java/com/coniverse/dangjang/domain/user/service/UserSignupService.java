package com.coniverse.dangjang.domain.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coniverse.dangjang.domain.auth.dto.OauthProvider;
import com.coniverse.dangjang.domain.auth.dto.request.KakaoLoginRequest;
import com.coniverse.dangjang.domain.auth.dto.request.NaverLoginRequest;
import com.coniverse.dangjang.domain.auth.dto.response.LoginResponse;
import com.coniverse.dangjang.domain.auth.mapper.AuthMapper;
import com.coniverse.dangjang.domain.auth.service.OauthLoginService;
import com.coniverse.dangjang.domain.guide.common.exception.GuideNotFoundException;
import com.coniverse.dangjang.domain.guide.weight.service.WeightGuideSearchService;
import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthMetricPostRequest;
import com.coniverse.dangjang.domain.healthmetric.service.HealthMetricRegisterService;
import com.coniverse.dangjang.domain.infrastructure.auth.dto.OAuthInfoResponse;
import com.coniverse.dangjang.domain.point.service.PointService;
import com.coniverse.dangjang.domain.user.dto.request.SignUpRequest;
import com.coniverse.dangjang.domain.user.dto.response.DuplicateNicknameResponse;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.entity.enums.ActivityAmount;
import com.coniverse.dangjang.domain.user.entity.enums.Gender;
import com.coniverse.dangjang.domain.user.mapper.UserMapper;
import com.coniverse.dangjang.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * USER Service
 *
 * @author EVE
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class UserSignupService {
	private final UserRepository userRepository;
	private final HealthMetricRegisterService healthMetricRegisterService;
	private final UserSearchService userSearchService;
	private final OauthLoginService oauthLoginService;
	private final PointService pointService;
	private final UserMapper userMapper;
	private final AuthMapper authMapper;
	private final WeightGuideSearchService weightGuideSearchService;

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
		User savedUser = userRepository.save(userMapper.toEntity(oAuthInfoResponse, signUpRequest, activityAmount, gender, recommendedCalorie));
		pointService.addSignupPoint(savedUser.getOauthId());
		registerWeight(savedUser, signUpRequest.weight());
		return authMapper.toLoginResponse(savedUser.getNickname(), false, savedUser.getHealthConnect().isConnecting());
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
	private OAuthInfoResponse getOauthInfo(OauthProvider provider, String accessToken) {
		if (provider.equals(OauthProvider.KAKAO)) {
			KakaoLoginRequest kakaoLoginRequest = new KakaoLoginRequest(accessToken);
			return oauthLoginService.request(kakaoLoginRequest);
		}
		NaverLoginRequest naverLoginRequest = new NaverLoginRequest(accessToken);
		return oauthLoginService.request(naverLoginRequest);
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

	private int calculateRecommendedCalorie(Gender gender, int height, ActivityAmount activityAmount) {
		double standardWeight;
		if (gender.isTrue()) {
			standardWeight = (Math.pow(height / 100.0, 2.0) * 21);
		} else {
			standardWeight = (Math.pow(height / 100.0, 2.0) * 22);
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
	 * Weight HealthMetric 등록
	 *
	 * @param user   사용자
	 * @param weight 체중
	 * @since 1.0.0
	 */

	private void registerWeight(User user, int weight) {
		try {
			weightGuideSearchService.findByUserIdAndCreatedAt(user.getOauthId(), user.getCreatedAt().toLocalDate().toString());
		} catch (GuideNotFoundException e) {
			HealthMetricPostRequest healthMetricPostRequest = HealthMetricPostRequest.builder()
				.type("체중")
				.createdAt(user.getCreatedAt().toLocalDate().toString())
				.unit(String.valueOf(weight))
				.build();
			healthMetricRegisterService.register(healthMetricPostRequest, user.getOauthId());
		}
	}

	/**
	 * 중복된 닉네임 확인
	 *
	 * @param nickname 확인이 필요한 닉네임
	 * @return 중복된 닉네임이면 false, 중복되지 않았으면 true를 담은 DuplicateNicknameResponse 객체 리턴
	 * @since 1.0.0
	 */
	public DuplicateNicknameResponse checkDuplicatedNickname(String nickname) { // TODO 수정 필요 (중복됐으면 에러뱉게)
		Optional<User> findNickname = userSearchService.findNickname(nickname);
		if (findNickname.isPresent()) {
			return new DuplicateNicknameResponse(false);
		} else {
			return new DuplicateNicknameResponse(true);
		}
	}
}
