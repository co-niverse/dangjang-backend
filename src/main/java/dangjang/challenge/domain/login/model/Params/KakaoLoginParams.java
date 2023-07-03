package dangjang.challenge.domain.login.model.Params;

import dangjang.challenge.domain.login.model.OAuthProvider;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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