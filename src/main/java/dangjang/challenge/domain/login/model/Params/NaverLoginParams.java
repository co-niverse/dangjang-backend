package dangjang.challenge.domain.login.model.Params;

import dangjang.challenge.domain.login.model.OAuthProvider;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NaverLoginParams implements OAuthLoginParams {
    private String accessToken;

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.NAVER;
    }

    @Override
    public String oAuthToken() {
        return accessToken;
    }

}
