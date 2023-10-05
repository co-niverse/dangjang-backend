package com.coniverse.dangjang.domain.auth.service;

import java.time.LocalDate;
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
import com.coniverse.dangjang.domain.infrastructure.auth.client.OAuthClient;
import com.coniverse.dangjang.domain.infrastructure.auth.dto.OAuthInfoResponse;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.exception.InvalidAuthenticationException;
import com.coniverse.dangjang.domain.user.exception.NonExistentUserException;
import com.coniverse.dangjang.domain.user.repository.UserRepository;
import com.coniverse.dangjang.domain.user.service.UserSearchService;
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

	public DefaultOauthLoginService(AuthTokenGenerator authTokenGenerator, UserSearchService userSearchService, List<OAuthClient> clients,
		UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
		this.authTokenGenerator = authTokenGenerator;
		this.userSearchService = userSearchService;
		this.clients = clients.stream().collect(
			Collectors.toUnmodifiableMap(OAuthClient::getOauthProvider, Function.identity())
		);
		this.userRepository = userRepository;
		this.jwtTokenProvider = jwtTokenProvider;
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
		JWTStatus jwtStatus = jwtTokenProvider.validationToken(token);
		if (jwtStatus.equals(JWTStatus.OK)) {
			Claims claim = jwtTokenProvider.parseClaims(token);
			User user = userSearchService.findUserByOauthId(claim.getSubject());
			return getAuthToken(user.getNickname());
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
}