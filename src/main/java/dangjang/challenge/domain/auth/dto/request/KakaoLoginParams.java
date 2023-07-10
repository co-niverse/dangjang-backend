package dangjang.challenge.domain.auth.dto.request;

import dangjang.challenge.domain.auth.dto.OAuthProvider;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 카카오 로그인 parm
 *
 * @author EVE
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class KakaoLoginParams implements OAuthLoginParams {
	private String accessToken;

	//provider 전달
	@Override
	public OAuthProvider oAuthProvider() {
		return OAuthProvider.KAKAO;
	}

	//accessToken 가져오기
	@Override
	public String oAuthToken() {
		return accessToken;
	}

}