package com.coniverse.dangjang.domain.auth.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import com.coniverse.dangjang.domain.auth.dto.AuthToken;
import com.coniverse.dangjang.domain.auth.dto.OauthProvider;
import com.coniverse.dangjang.domain.auth.dto.request.OauthLoginRequest;
import com.coniverse.dangjang.domain.auth.dto.response.LoginResponse;
import com.coniverse.dangjang.domain.auth.entity.blackToken;
import com.coniverse.dangjang.domain.auth.repository.blackTokenRepository;
import com.coniverse.dangjang.domain.infrastructure.auth.client.OAuthClient;
import com.coniverse.dangjang.domain.infrastructure.auth.dto.OAuthInfoResponse;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.exception.InvalidAuthenticationException;
import com.coniverse.dangjang.domain.user.exception.NonExistentUserException;
import com.coniverse.dangjang.domain.user.repository.UserRepository;
import com.coniverse.dangjang.domain.user.service.UserSearchService;
import com.coniverse.dangjang.global.exception.BlackTokenException;
import com.coniverse.dangjang.global.exception.InvalidTokenException;
import com.coniverse.dangjang.global.support.enums.JWTStatus;

import io.jsonwebtoken.Claims;

/**
 * oauth 로그인 서비스
 *
 * @author EVE, TEO
 * @since 1.0.0
 */
@Service
@Transactional
public class DefaultOauthLoginService implements OauthLoginService {
	private final AuthTokenGenerator authTokenGenerator;
	private final UserSearchService userSearchService;
	private final Map<OauthProvider, OAuthClient> clients;
	private final UserRepository userRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final blackTokenRepository blackTokenRepository;

	public DefaultOauthLoginService(AuthTokenGenerator authTokenGenerator, UserSearchService userSearchService, List<OAuthClient> clients,
		UserRepository userRepository, JwtTokenProvider jwtTokenProvider, blackTokenRepository blackTokenRepository) {
		this.authTokenGenerator = authTokenGenerator;
		this.userSearchService = userSearchService;
		this.clients = clients.stream().collect(
			Collectors.toUnmodifiableMap(OAuthClient::getOauthProvider, Function.identity())
		);
		this.userRepository = userRepository;
		this.jwtTokenProvider = jwtTokenProvider;
		this.blackTokenRepository = blackTokenRepository;
	}

	/**
	 * @param params 카카오,네이버 accessToken
	 * @return Content 로그인을 성공하면, JWT TOKEN과 사용자 정보(nickname, authID)를 전달한다.
	 * @since 1.0.0
	 */
	public LoginResponse login(OauthLoginRequest params) {
		OAuthInfoResponse oAuthInfoResponse = request(params);
		User user = userSearchService.findUserByOauthId(oAuthInfoResponse.getOauthId());
		return new LoginResponse(user.getNickname(), false, false);
	}

	/**
	 * refreshToken 인증 후 AuthToken 재발급
	 *
	 * @param header
	 * @return AuthToken 재발급된 AccessToken과 refreshToken을 전달한다
	 * @since 1.0.0
	 */

	public AuthToken reissueToken(String header) {
		String token = jwtTokenProvider.getToken(header);
		Claims claim = checkJwtTokenValidation(token);
		User user = userSearchService.findUserByOauthId(claim.getSubject());
		return getAuthToken(user.getNickname());
	}

	/**
	 * jwtToken 검증
	 * <p>
	 * 만약 유효한 Token이면 Claims을 반환한다.
	 *
	 * @param token
	 * @return Claims
	 * @throws InvalidTokenException 유효하지 않은 토큰일 경우
	 * @since 1.0.0
	 */
	public Claims checkJwtTokenValidation(String token) {
		JWTStatus jwtStatus = jwtTokenProvider.validationToken(token);
		if (jwtStatus.equals(JWTStatus.OK)) {
			return jwtTokenProvider.parseClaims(token);
		}
		throw new InvalidTokenException(jwtStatus.getMessage());
	}

	/**
	 * @param nickname
	 * @return AuthToken 로그인을 성공한 사용자의 authToken을 전달
	 * @since 1.0.0
	 */

	public AuthToken getAuthToken(String nickname) {
		Optional<User> user = userRepository.findByNickname(nickname);
		if (user.isPresent()) {
			return authTokenGenerator.generate(user.get().getOauthId(), user.get().getRole());
		} else {
			throw new NonExistentUserException();
		}
	}

	/**
	 * @param params 카카오,네이버 accessToken
	 * @return OAuthInfoResponse 카카오, 네이버 사용자 정보
	 * @throws InvalidAuthenticationException 유효하지 않은 인증
	 * @since 1.0.0
	 */
	@Override
	public OAuthInfoResponse request(OauthLoginRequest params) {

		try {
			OAuthClient client = clients.get(params.getOauthProvider());
			String accessToken = params.getAccessToken();
			return client.requestOauthInfo(accessToken);
		} catch (HttpClientErrorException.Unauthorized e) {
			throw new InvalidAuthenticationException();
		}

	}

	/**
	 * 유저 접속일자를 업데이트한다.
	 *
	 * @return LocalDate
	 * @since 1.0.0
	 */
	public void updateUserAccessedAt(User user) {
		userRepository.updateAccessedAtByOauthId(user.getOauthId(), LocalDate.now());
	}

	/**
	 * 로그아웃
	 *
	 * @param accessToken
	 * @since 1.0.0
	 */

	public void logout(String accessToken) {
		//TODO: Refresh Token Redis에서 삭제
		jwtTokenBlack(jwtTokenProvider.getToken(accessToken));
	}

	/**
	 * jwtToken을 Black 처리한다
	 *
	 * @param token blackList에 추가될 jwtToken
	 * @since 1.0.0
	 */

	private void jwtTokenBlack(String token) {
		Claims claim = checkJwtTokenValidation(token);
		long blackTokenExpirationTime = calculateExpirationTime(claim.getExpiration().getTime());
		blackToken blackToken = com.coniverse.dangjang.domain.auth.entity.blackToken.builder() //TODO: mapper 패턴으로 변경
			.token(token)
			.expirationTime(blackTokenExpirationTime) //TODO: 유효 시간 계산
			.build();
		blackTokenRepository.save(blackToken);
	}

	/**
	 * ExpiredTime 계산
	 *
	 * @param expiration jwtToken 만료 시간
	 * @return long 만료 시간까지의 Second
	 * @since 1.0.0
	 */

	private long calculateExpirationTime(long expiration) {
		long now = new Date().getTime();
		return (expiration - now) / 1000;
	}

	/**
	 * Black된 토큰인지 확인한다
	 *
	 * @param token 확인할 토큰
	 * @throws BlackTokenException Black된 토큰일 경우
	 * @since 1.0.0
	 */
	public void validBlackToken(String token) {
		if (blackTokenRepository.findById(token).isPresent()) {
			throw new BlackTokenException();
		}
	}
}