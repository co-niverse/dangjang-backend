package dangjang.challenge.domain.auth.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.web.client.HttpClientErrorException;

import dangjang.challenge.domain.auth.dto.AuthToken.AuthTokens;
import dangjang.challenge.domain.auth.dto.request.KakaoLoginParams;
import dangjang.challenge.domain.user.dto.UserInfo;
import dangjang.challenge.domain.user.infrastructure.KakaoInfoResponse;
import dangjang.challenge.domain.user.service.UserService;
import dangjang.challenge.global.exception.NonExistentUserException;

/**
 * @author EVE
 * @since 1.0
 */
@SpringBootTest
public class OAuthLoginServiceTest {
	@Autowired
	private OAuthLoginService oAuthLoginService;
	@Autowired
	private AuthTokensGenerator authTokensGenerator;
	@Autowired
	private UserService userService;

	@Test
	@WithAnonymousUser
	void 로그인_실패() throws Exception { //처음부터 오류가 발생해서 coverage 낮은 듯

		//given
		KakaoLoginParams kakaoLoginParams = new KakaoLoginParams();
		kakaoLoginParams.setAccessToken("4J-zgwK68lN3RIm8iy1Qv0EGE54mbyOrVc-X1cf1CinJXgAAAYk1SMch");

		Assertions.assertThrows(HttpClientErrorException.Unauthorized.class, () -> {
			oAuthLoginService.login(kakaoLoginParams);
		});

	}

	@Test
	@WithAnonymousUser
	void JWT_반환() throws Exception {
		// given
		//유저 생성
		KakaoInfoResponse kakaoInfoResponse = new KakaoInfoResponse();
		kakaoInfoResponse.setId(2878733655L);
		kakaoInfoResponse.setConnected_at(new Date());
		Long userId = userService.newMember(kakaoInfoResponse);

		//유저 존재 확인
		UserInfo userInfo = userService.findUser(kakaoInfoResponse);
		if (userInfo != null) {
			AuthTokens authTokens = authTokensGenerator.generate(userInfo.getOauthId());
			assertThat(authTokens).isNotNull();
		}
	}

	@Test()
	void 새로운_유저_추가() throws Exception {
		//given
		KakaoInfoResponse kakaoInfoResponse = new KakaoInfoResponse();
		kakaoInfoResponse.setId(2878733655L);
		kakaoInfoResponse.setConnected_at(new Date());
		Long userId = userService.newMember(kakaoInfoResponse);
		assertThat(userId).isNotNull();
	}

	@Test()
	void 유저_여부_확인하기() throws Exception {
		//given
		KakaoInfoResponse kakaoInfoResponse = new KakaoInfoResponse();
		kakaoInfoResponse.setId(2878733654L);
		kakaoInfoResponse.setConnected_at(new Date());
		// UserInfo userInfo = oAuthLoginService.findUser(kakaoInfoResponse);

		Assertions.assertThrows(NonExistentUserException.class, () -> {
			userService.findUser(kakaoInfoResponse);
		});
	}
}
