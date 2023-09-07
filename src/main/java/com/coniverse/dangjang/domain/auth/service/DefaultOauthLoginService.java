package com.coniverse.dangjang.domain.auth.service;

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

	public DefaultOauthLoginService(AuthTokenGenerator authTokenGenerator, UserSearchService userSearchService, List<OAuthClient> clients,
		UserRepository userRepository) {
		this.authTokenGenerator = authTokenGenerator;
		this.userSearchService = userSearchService;
		this.clients = clients.stream().collect(
			Collectors.toUnmodifiableMap(OAuthClient::getOauthProvider, Function.identity())
		);
		this.userRepository = userRepository;
	}

	/**
	 * @param params 카카오,네이버 accessToken
	 * @return Content 로그인을 성공하면, JWT TOKEN과 사용자 정보(nickname, authID)를 전달한다.
	 * @since 1.0.0
	 */
	public LoginResponse login(OauthLoginRequest params) {
		// OAuthInfoResponse oAuthInfoResponse = request(params); //TODO : 커밋 전에 수정
		User user = userSearchService.findUserByOauthId("11111111"); //TODO : oAuthInfoResponse.getOauthId()
		return new LoginResponse(user.getNickname(), false, false);
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
}